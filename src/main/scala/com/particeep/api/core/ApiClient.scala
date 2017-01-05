package com.particeep.api.core

import com.ning.http.client.AsyncHttpClient
import play.api.libs.ws._
import play.api.libs.ws.ning._

import scala.concurrent.ExecutionContext

case class ApiCredential(apiKey: String, apiSecret: String)

trait WSClient {
  def cleanup(): Unit

  /**
   * @param path : relative path for the request
   * @param timeOut : Sets the maximum time in milliseconds you expect the request to take. default : infinite
   * @param exec : Execution context for the request
   * @return
   */
  def url(path: String, timeOut: Long = -1)(implicit exec: ExecutionContext): WSRequest
  def urlFileUpload(path: String, client: AsyncHttpClient, timeOut: Long = -1)(implicit exec: ExecutionContext): AsyncHttpClient#BoundRequestBuilder
}

trait BaseClient {
  self: WSClient =>

  // TODO: allow better config of WS
  // For more fine grain on config see https://www.playframework.com/documentation/2.4.x/ScalaWS
  //    val config = new NingAsyncHttpClientConfigBuilder(DefaultWSClientConfig()).build
  //    val builder = new AsyncHttpClientConfig.Builder(config)
  //    val client = new NingWSClient(builder.build)
  protected implicit val sslClient = NingWSClient()

  def cleanup() = {
    sslClient.close()
  }

}

class ApiClient(val baseUrl: String, val apiCredential: ApiCredential, val version: String) extends WSClient with BaseClient with WithSecurtiy {

  def url(path: String, timeOut: Long = -1)(implicit exec: ExecutionContext): WSRequest = {
    val req = WS.clientUrl(s"$baseUrl/v$version$path")
    secure(req, apiCredential, timeOut)
  }

  def urlFileUpload(path: String, client: AsyncHttpClient, timeOut: Long = -1)(implicit exec: ExecutionContext): AsyncHttpClient#BoundRequestBuilder = {
    val postBuilder = client.preparePost(s"$baseUrl/v$version$path")
    secure(postBuilder, apiCredential, timeOut)
  }
}

object ApiClient {

  /**
   * usage
   * val ws = new ApiClient(
   *   "https://api.particeep.com",
   *   creds,
   *   "1"
   * ) with UserCapability
   *
   * val result:Future[Either[JsError, Info]] = ws.user.byId("some_id")
   */
  def apply(baseUrl: String, apiCredential: ApiCredential, version: String): ApiClient = {
    ApiClient(baseUrl, apiCredential, version)
  }
}
