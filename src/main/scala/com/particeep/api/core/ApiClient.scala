package com.particeep.api.core

import com.ning.http.client.multipart.{ FilePart, Part }
import com.ning.http.client.{ AsyncHttpClient, Response }
import play.api.libs.Files.TemporaryFile
import play.api.libs.ws._
import play.api.libs.ws.ning._
import play.api.Play.current
import play.api.mvc.MultipartFormData

import scala.concurrent.{ ExecutionContext, Future }

case class ApiCredential(apiKey: String, apiSecret: String)

trait WSClient {
  def cleanup(): Unit

  /**
   * @param path : relative path for the request
   * @param timeOut : Sets the maximum time in milliseconds you expect the request to take. default : infinite
   * @param exec : Execution context for the request
   * @return
   */
  def url(path: String, timeOut: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): WSRequest
  def postFile(path: String, file: MultipartFormData[TemporaryFile], bodyParts: List[Part], timeOut: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Response]
}

trait BaseClient {
  self: WSClient =>

  // TODO: allow better config of WS
  // For more fine grain on config see https://www.playframework.com/documentation/2.4.x/ScalaWS
  //    val config = new NingAsyncHttpClientConfigBuilder(DefaultWSClientConfig()).build
  //    val builder = new AsyncHttpClientConfig.Builder(config)
  //    val client = new NingWSClient(builder.build)
  protected implicit val sslClient = ApiClient.defaultSslClient

  def cleanup() = {
    sslClient.close()
  }

}

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
class ApiClient(val baseUrl: String, val version: String) extends WSClient with BaseClient with WithSecurtiy {

  def url(path: String, timeOut: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): WSRequest = {
    val req = WS.clientUrl(s"$baseUrl/v$version$path")
    secure(req, credentials, timeOut)
  }

  def postFile(path: String, file: MultipartFormData[TemporaryFile], bodyParts: List[Part], timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Response] = {
    val documentFilePart = file.files.head
    val client = WS.client.underlying[AsyncHttpClient]
    val postBuilder = urlFileUpload(path, client, timeout)
    val builder = postBuilder.addBodyPart(
      new FilePart("document", documentFilePart.ref.file, documentFilePart.contentType.getOrElse("application/octet-stream"))
    )
    bodyParts.map(builder.addBodyPart(_))
    Future { client.executeRequest(builder.build()).get }
  }

  private[this] def urlFileUpload(path: String, client: AsyncHttpClient, timeOut: Long)(implicit exec: ExecutionContext, credentials: ApiCredential): AsyncHttpClient#BoundRequestBuilder = {
    val postBuilder = client.preparePost(s"$baseUrl/v$version$path")
    secure(postBuilder, credentials, timeOut)
  }
}

object ApiClient {

  lazy val defaultSslClient = NingWSClient()
}
