package com.particeep.api.core

import com.ning.http.client.multipart.{ FilePart, Part }
import com.ning.http.client.{ AsyncHttpClient, Response }
import play.api.libs.Files.TemporaryFile
import play.api.libs.ws._
import play.api.libs.ws.ahc.StandaloneAhcWSClient
import play.api.mvc.MultipartFormData

import scala.concurrent.{ ExecutionContext, Future }

case class ApiCredential(apiKey: String, apiSecret: String)

trait WSClient {
  def cleanup(): Unit = ()
  def credentials(): Option[ApiCredential]

  /**
   * @param path : relative path for the request
   * @param timeOut : Sets the maximum time in milliseconds you expect the request to take. default : infinite
   * @param exec : Execution context for the request
   * @return
   */
  def url(path: String, timeOut: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): StandaloneWSRequest
  def postFile(path: String, file: MultipartFormData[TemporaryFile], bodyParts: List[Part], timeOut: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Response]
}

trait BaseClient {
  self: WSClient =>

}

/**
 * usage
 * val ws = new ApiClient(
 *   "https://api.particeep.com",
 *   "1",
 *   creds
 * ) with UserCapability
 *
 * val result:Future[Either[JsError, Info]] = ws.user.byId("some_id")
 */
class ApiClient(val ws: StandaloneAhcWSClient, val baseUrl: String, val version: String, val credentials: Option[ApiCredential] = None) extends WSClient with BaseClient with WithSecurtiy {

  def url(path: String, timeOut: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): StandaloneWSRequest = {
    val req = ws.url(s"$baseUrl/v$version$path")
    secure(req, credentials, timeOut)
  }

  def postFile(path: String, file: MultipartFormData[TemporaryFile], bodyParts: List[Part], timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Response] = {
    val documentFilePart = file.files.head
    val client = ws.underlying[AsyncHttpClient]
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
