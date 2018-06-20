package com.particeep.api.core

import com.ning.http.client.multipart.{ FilePart, Part }
import com.ning.http.client.AsyncHttpClient
import com.particeep.api.models.{ Error, ErrorResult, Errors }
import play.api.libs.Files.TemporaryFile
import play.api.libs.ws._
import play.api.libs.ws.ning._
import play.api.Play.current
import play.api.libs.iteratee.Enumerator
import play.api.libs.json.{ Format, JsValue, Json }
import play.api.mvc.MultipartFormData

import scala.concurrent.{ ExecutionContext, Future }
import scala.util.Random
import scala.util.control.NonFatal

case class ApiCredential(apiKey: String, apiSecret: String, http_headers: Option[Seq[(String, String)]] = None) {
  def withHeader(name: String, value: String): ApiCredential = {
    val new_value = (name, value) :: this.http_headers.map(_.toList).getOrElse(List())
    this.copy(http_headers = Some(new_value))
  }
}

trait WSClient {
  val defaultTimeOut: Long
  val defaultImportTimeOut: Long

  def cleanup(): Unit
  def credentials(): Option[ApiCredential]

  /**
   * @param path : relative path for the request
   * @param timeOut : Sets the maximum time in milliseconds you expect the request to take. default : infinite
   * @param exec : Execution context for the request
   * @return
   */
  def get[T](
    path:    String,
    timeOut: Long,
    params:  List[(String, String)] = List()
  )(implicit exec: ExecutionContext, credentials: ApiCredential, f: Format[T]): Future[Either[ErrorResult, T]]

  def post[T](
    path:    String,
    timeOut: Long,
    body:    JsValue,
    params:  List[(String, String)] = List()
  )(implicit exec: ExecutionContext, credentials: ApiCredential, f: Format[T]): Future[Either[ErrorResult, T]]

  def put[T](
    path:    String,
    timeOut: Long,
    body:    JsValue
  )(implicit exec: ExecutionContext, credentials: ApiCredential, f: Format[T]): Future[Either[ErrorResult, T]]

  def delete[T](
    path:    String,
    timeOut: Long,
    body:    JsValue                = Json.toJson(""),
    params:  List[(String, String)] = List()
  )(implicit exec: ExecutionContext, credentials: ApiCredential, f: Format[T]): Future[Either[ErrorResult, T]]

  def postFile[T](
    path:      String,
    timeOut:   Long,
    file:      MultipartFormData[TemporaryFile],
    bodyParts: List[Part]
  )(implicit exec: ExecutionContext, credentials: ApiCredential, f: Format[T]): Future[Either[ErrorResult, T]]

  def getStream(
    path:    String,
    timeOut: Long,
    params:  List[(String, String)] = List()
  )(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, Enumerator[Array[Byte]]]]

  def postStream(
    path:    String,
    timeOut: Long,
    body:    JsValue,
    params:  List[(String, String)] = List()
  )(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, Enumerator[Array[Byte]]]]
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
 *   "1",
 *   creds
 * ) with UserCapability
 *
 * val result:Future[Either[JsError, Info]] = ws.user.byId("some_id")
 */
class ApiClient(val baseUrl: String, val version: String, val credentials: Option[ApiCredential] = None) extends WSClient with BaseClient with WithSecurtiy with ResponseParser {

  val defaultTimeOut: Long = 10000
  val defaultImportTimeOut: Long = -1

  private[this] def url(path: String, timeOut: Long)(implicit exec: ExecutionContext, credentials: ApiCredential): WSRequest = {
    val req = WS.clientUrl(s"$baseUrl/v$version$path")
    secure(req, credentials, timeOut).withHeaders(credentials.http_headers.getOrElse(List()): _*)
  }

  private[this] def urlFileUpload(path: String, client: AsyncHttpClient, timeOut: Long)(implicit exec: ExecutionContext, credentials: ApiCredential): AsyncHttpClient#BoundRequestBuilder = {
    val postBuilder = client.preparePost(s"$baseUrl/v$version$path")
    val url = secure(postBuilder, credentials, timeOut)
    credentials.http_headers.map(_.foldLeft(url) { (acc, elem) =>
      acc.addHeader(elem._1, elem._2)
    }).getOrElse(url)
  }

  def get[T](path: String, timeOut: Long, params: List[(String, String)] = List())(implicit exec: ExecutionContext, credentials: ApiCredential, f: Format[T]): Future[Either[ErrorResult, T]] = {
    url(path, timeOut).withQueryString(params: _*).get().map(parse[T](_)).recover {
      case NonFatal(e) => handle_error(e, "GET", path)
    }
  }

  def post[T](
    path:    String,
    timeOut: Long,
    body:    JsValue,
    params:  List[(String, String)] = List()
  )(implicit exec: ExecutionContext, credentials: ApiCredential, f: Format[T]): Future[Either[ErrorResult, T]] = {
    url(path, timeOut).withQueryString(params: _*).post(body).map(parse[T](_)).recover {
      case NonFatal(e) => handle_error(e, "POST", path)
    }
  }

  def put[T](path: String, timeOut: Long, body: JsValue)(implicit exec: ExecutionContext, credentials: ApiCredential, f: Format[T]): Future[Either[ErrorResult, T]] = {
    url(path, timeOut).put(body).map(parse[T](_)).recover {
      case NonFatal(e) => handle_error(e, "PUT", path)
    }
  }

  def delete[T](
    path:    String,
    timeOut: Long,
    body:    JsValue                = Json.toJson(""),
    params:  List[(String, String)] = List()
  )(implicit exec: ExecutionContext, credentials: ApiCredential, f: Format[T]): Future[Either[ErrorResult, T]] = {
    url(path, timeOut).withQueryString(params: _*).withMethod("DELETE").withBody(body).execute().map(parse[T](_)).recover {
      case NonFatal(e) => handle_error(e, "DELETE", path)
    }
  }

  def postFile[T](
    path:      String,
    timeout:   Long,
    file:      MultipartFormData[TemporaryFile],
    bodyParts: List[Part]
  )(implicit exec: ExecutionContext, credentials: ApiCredential, f: Format[T]): Future[Either[ErrorResult, T]] = {
    val documentFilePart = file.files.head
    val client = WS.client.underlying[AsyncHttpClient]
    val postBuilder = urlFileUpload(path, client, timeout)
    val builder = postBuilder.addBodyPart(
      new FilePart("document", documentFilePart.ref.file, documentFilePart.contentType.getOrElse("application/octet-stream"))
    )
    bodyParts.map(builder.addBodyPart(_))
    Future { client.executeRequest(builder.build()).get }.map(parse[T](_)).recover {
      case NonFatal(e) => handle_error(e, "DELETE", path)
    }
  }

  def getStream(
    path:    String,
    timeOut: Long,
    params:  List[(String, String)] = List()
  )(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, Enumerator[Array[Byte]]]] = {
    parseStream(url(path, timeOut).withQueryString(params: _*).withMethod("GET")).recover {
      case NonFatal(e) => handle_error[Enumerator[Array[Byte]]](e, "GET", path)
    }
  }

  def postStream(
    path:    String,
    timeOut: Long,
    body:    JsValue,
    params:  List[(String, String)] = List()
  )(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, Enumerator[Array[Byte]]]] = {
    parseStream(url(path, timeOut).withQueryString(params: _*).withMethod("POST").withBody(body)).recover {
      case NonFatal(e) => handle_error[Enumerator[Array[Byte]]](e, "POST", path)
    }
  }

  private[this] def handle_error[T](e: Throwable, method: String, path: String): Either[ErrorResult, T] = {
    val technical_code = "error.api.no.response"
    val error_msg = s"ApiClient error for $method method on path $path"
    val error_id = "#" + Random.alphanumeric.take(8).mkString
    Left(Errors(true, List(Error(technical_code, error_msg, Some(error_id), Some(e.getMessage)))))
  }
}

object ApiClient {

  lazy val defaultSslClient = NingWSClient()
}
