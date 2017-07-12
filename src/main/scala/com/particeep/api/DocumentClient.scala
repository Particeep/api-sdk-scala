package com.particeep.api

import com.ning.http.client.multipart.StringPart
import com.particeep.api.core._
import com.particeep.api.models.document._
import com.particeep.api.models.{ ErrorResult, PaginatedSequence }
import com.particeep.api.utils.LangUtils
import play.api.libs.Files.TemporaryFile
import play.api.libs.json.Json
import play.api.mvc.MultipartFormData

import scala.concurrent.{ ExecutionContext, Future }

/**
 * Created by Noe on 27/12/2016.
 */
trait DocumentCapability {
  self: WSClient =>

  val document: DocumentClient = new DocumentClient(this)

  def document(credentials: ApiCredential): DocumentClient = new DocumentClient(this, Some(credentials))
}

object DocumentClient {
  private val endPoint: String = "/document"
  private implicit val format = Document.format
  private implicit val format_creation = DocumentCreation.format
  private implicit val format_edition = DocumentEdition.format
}

class DocumentClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends ResponseParser with WithWS with WithCredentials with EntityClient {

  import DocumentClient._

  def upload(
    owner_id:          String,
    file:              MultipartFormData[TemporaryFile],
    document_creation: DocumentCreation,
    timeout:           Long                             = -1
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, Document]] = {
    val bodyParts = List(
      new StringPart("target_id", document_creation.target_id.getOrElse("")),
      new StringPart("target_type", document_creation.target_type.getOrElse("")),
      new StringPart("description", document_creation.description.getOrElse("")),
      new StringPart("name", document_creation.name.getOrElse("")),
      new StringPart("path", document_creation.path.getOrElse("")),
      new StringPart("locked", document_creation.locked.getOrElse(false).toString),
      new StringPart("override_existing_file", document_creation.override_existing_file.getOrElse(false).toString)
    )
    ws.postFile(s"$endPoint/$owner_id/upload", file, bodyParts, timeout).map(parse[Document])
  }

  def createDir(owner_id: String, document_creation: DocumentCreation, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Document]] = {
    ws.url(s"$endPoint/$owner_id/dir", timeout).post(Json.toJson(document_creation)).map(parse[Document])
  }

  def download(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Stream[Byte]]] = {
    ws.url(s"$endPoint/download/$id", timeout).get().map(parse[Stream[Byte]])
  }

  def byId(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Document]] = {
    ws.url(s"$endPoint/$id", timeout).get().map(parse[Document])
  }

  def byIds(ids: Seq[String], timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[Document]]] = {
    ws.url(s"$endPoint", timeout)
      .withQueryString("ids" -> ids.mkString(","))
      .get()
      .map(parse[List[Document]])
  }

  def update(id: String, document_edition: DocumentEdition, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Document]] = {
    ws.url(s"$endPoint/$id", timeout).post(Json.toJson(document_edition)).map(parse[Document])
  }

  def listDir(
    path:        String,
    target_id:   Option[String],
    target_type: Option[String],
    timeout:     Long           = -1
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[FolderOrFile]]] = {
    val params: List[(String, String)] = List(("path", path)) ++
      target_id.map(x => List(("target_id", x))).getOrElse(List()) ++
      target_type.map(x => List(("target_type", x))).getOrElse(List())

    ws.url(s"$endPoint/dir", timeout).withQueryString(params: _*).get().map(parse[List[FolderOrFile]])
  }

  def search(criteria: DocumentSearch, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[Document]]] = {
    ws.url(s"$endPoint/search", timeout).withQueryString(LangUtils.productToQueryString(criteria): _*).get().map(parse[PaginatedSequence[Document]])
  }

  def delete(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Document]] = {
    ws.url(s"$endPoint/$id", timeout).delete().map(parse[Document])
  }
}
