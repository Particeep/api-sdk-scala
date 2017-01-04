package com.particeep.api

import com.ning.http.client.{AsyncHttpClient, ListenableFuture, Response}
import com.ning.http.client.multipart.{FilePart, StringPart}
import com.particeep.api.core.{ResponseParser, WSClient}
import com.particeep.api.models.{ErrorResult, PaginatedSequence}
import com.particeep.api.utils.LangUtils
import play.api.Play.current
import play.api.libs.Files.TemporaryFile
import play.api.libs.json.Json
import play.api.libs.ws.WS
import play.api.mvc.MultipartFormData

import scala.concurrent.{ExecutionContext, Future}

/**
 * Created by Noe on 27/12/2016.
 */
case class DocumentApi(
  id:                     String          = "",
  owner_id:               String          = "",
  target_id:              Option[String]  = None,
  target_type:            Option[String]  = None,
  name:                   String          = "",
  description:            Option[String]  = None,
  content_type:           Option[String]  = None,
  path:                   String          = "",
  doc_type:               String          = "",
  locked:                 Boolean         = false,
  override_existing_file: Option[Boolean] = None
)

case class DocumentCreation(
  id:                     String          = "",
  owner_id:               String          = "",
  target_id:              Option[String]  = None,
  target_type:            Option[String]  = None,
  name:                   String          = "",
  description:            Option[String]  = None,
  content_type:           Option[String]  = None,
  path:                   String          = "",
  doc_type:               String          = "",
  locked:                 Boolean         = false,
  override_existing_file: Option[Boolean] = None
)

case class DocumentSearchCriteria(
  owner_id:     Option[String] = None,
  target_id:    Option[String] = None,
  target_type:  Option[String] = None,
  name:         Option[String] = None,
  description:  Option[String] = None,
  content_type: Option[String] = None,
  external_id:  Option[String] = None,
  sort_by:      Option[String] = None,
  order_by:     Option[String] = Some("asc"),
  offset:       Option[Int]    = Some(0),
  limit:        Option[Int]    = Some(30)
)

object DocumentApiTransformation {
  implicit val format = Json.format[DocumentApi]
}

trait DocumentCapability {
  self: WSClient =>

  val document: DocumentClient = new DocumentClient(this)
}

class DocumentClient(ws: WSClient) extends ResponseParser {

  import DocumentApiTransformation._

  private val endPoint: String = "/document"

  def upload(owner_id: String, file: MultipartFormData[TemporaryFile], document: DocumentCreation)(implicit exec: ExecutionContext): Future[Either[ErrorResult, DocumentApi]] = {
    val url = s"$endPoint/$owner_id/upload"
    Future { prepareRequest(url, file, document).get() }.map(parse[DocumentApi])
  }

  private def prepareRequest(url: String, file: MultipartFormData[TemporaryFile], document: DocumentCreation)(implicit exec: ExecutionContext): ListenableFuture[Response] = {
    val documentFilePart = file.files.head
    val client = WS.client.underlying[AsyncHttpClient]
    val postBuilder = ws.url(url, client)
    val builder = postBuilder.addBodyPart(
      new FilePart("document", documentFilePart.ref.file, documentFilePart.contentType.getOrElse("application/octet-stream"))
    )
    addDocumentApiToBody(postBuilder, document)
    client.executeRequest(builder.build())
  }

  private def addDocumentApiToBody(builder: AsyncHttpClient#BoundRequestBuilder, document: DocumentCreation) {
    builder.addBodyPart(new StringPart("target_id", document.target_id.map(_.toString).getOrElse("")))
      .addBodyPart(new StringPart("target_type", document.target_type.getOrElse("")))
      .addBodyPart(new StringPart("description", document.description.getOrElse("")))
      .addBodyPart(new StringPart("name", document.name))
      .addBodyPart(new StringPart("path", document.path))
      .addBodyPart(new StringPart("locked", document.locked.toString))
      .addBodyPart(new StringPart("override_existing_file", document.override_existing_file.getOrElse(false).toString))
  }

  def byId(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, DocumentApi]] = {
    ws.url(s"$endPoint/$id", timeout).get().map(parse[DocumentApi])
  }

  def listDir(path: String, target_id: Option[String], target_type: Option[String], timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, DocumentApi]] = {
    val params: List[(String, String)] = List(("path", path)) ++
      target_id.map(x => List(("target_id", x))).getOrElse(List()) ++
      target_type.map(x => List(("target_type", x))).getOrElse(List())

    ws.url(s"$endPoint/dir", timeout).withQueryString(params: _*).get().map(parse[DocumentApi])
  }

  def search(criteria: DocumentSearchCriteria, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[DocumentApi]]] = {
    ws.url(s"$endPoint/search", timeout).withQueryString(LangUtils.productToQueryString(criteria): _*).get().map(parse[PaginatedSequence[DocumentApi]])
  }

  def delete(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, DocumentApi]] = {
    ws.url(s"$endPoint/$id", timeout).delete().map(parse[DocumentApi])
  }
}