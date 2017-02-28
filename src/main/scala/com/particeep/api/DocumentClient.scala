package com.particeep.api

import com.ning.http.client.{ AsyncHttpClient, ListenableFuture, Response }
import com.ning.http.client.multipart.{ FilePart, StringPart }
import com.particeep.api.core.{ ApiCredential, ResponseParser, WSClient }
import com.particeep.api.models.document._
import com.particeep.api.models.{ ErrorResult, PaginatedSequence }
import com.particeep.api.utils.LangUtils
import play.api.Play.current
import play.api.libs.Files.TemporaryFile
import play.api.libs.json.Json
import play.api.libs.ws.WS
import play.api.mvc.MultipartFormData

import scala.concurrent.{ ExecutionContext, Future }

/**
 * Created by Noe on 27/12/2016.
 */

trait DocumentCapability {
  self: WSClient =>

  val document: DocumentClient = new DocumentClient(this)
}

class DocumentClient(ws: WSClient) extends ResponseParser {

  private[this] val endPoint: String = "/document"
  implicit val format = Document.format
  implicit val format_creation = DocumentCreation.format
  implicit val format_search = DocumentSearch.format
  implicit val format_edition = DocumentEdition.format
  implicit val write_folder_or_file = FolderOrFile.writes

  def upload(owner_id: String, file: MultipartFormData[TemporaryFile], document_creation: DocumentCreation, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, Document]] = {
    val url = s"$endPoint/$owner_id/upload"
    Future { prepareRequest(url, file, document_creation, timeout).get() }.map(parse[Document])
  }

  private[this] def prepareRequest(url: String, file: MultipartFormData[TemporaryFile], document_creation: DocumentCreation, timeout: Long)(implicit exec: ExecutionContext, credentials: ApiCredential): ListenableFuture[Response] = {
    val documentFilePart = file.files.head
    val client = WS.client.underlying[AsyncHttpClient]
    val postBuilder = ws.urlFileUpload(url, client, timeout)
    val builder = postBuilder.addBodyPart(
      new FilePart("document", documentFilePart.ref.file, documentFilePart.contentType.getOrElse("application/octet-stream"))
    )
    addDocumentApiToBody(postBuilder, document_creation)
    client.executeRequest(builder.build())
  }

  private[this] def addDocumentApiToBody(builder: AsyncHttpClient#BoundRequestBuilder, document_creation: DocumentCreation) {
    builder.addBodyPart(new StringPart("target_id", document_creation.target_id.getOrElse("")))
      .addBodyPart(new StringPart("target_type", document_creation.target_type.getOrElse("")))
      .addBodyPart(new StringPart("description", document_creation.description.getOrElse("")))
      .addBodyPart(new StringPart("name", document_creation.name.getOrElse("")))
      .addBodyPart(new StringPart("path", document_creation.path.getOrElse("")))
      .addBodyPart(new StringPart("locked", document_creation.locked.getOrElse(false).toString))
      .addBodyPart(new StringPart("override_existing_file", document_creation.override_existing_file.getOrElse(false).toString))
  }

  def createDir(owner_id: String, document_creation: DocumentCreation, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, Document]] = {
    ws.url(s"$endPoint/$owner_id/dir", timeout).post(Json.toJson(document_creation)).map(parse[Document])
  }

  def download(id: String, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, Stream[Byte]]] = {
    ws.url(s"$endPoint/download/$id", timeout).get().map(parse[Stream[Byte]])
  }

  def byId(id: String, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, Document]] = {
    ws.url(s"$endPoint/$id", timeout).get().map(parse[Document])
  }

  def byIds(ids: Seq[String], timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, List[Document]]] = {
    ws.url(s"$endPoint", timeout)
      .withQueryString("ids" -> ids.mkString(","))
      .get()
      .map(parse[List[Document]])
  }

  def update(id: String, document_edition: DocumentEdition, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, Document]] = {
    ws.url(s"$endPoint/$id", timeout).post(Json.toJson(document_edition)).map(parse[Document])
  }

  def listDir(path: String, target_id: Option[String], target_type: Option[String], timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, List[FolderOrFile]]] = {
    val params: List[(String, String)] = List(("path", path)) ++
      target_id.map(x => List(("target_id", x))).getOrElse(List()) ++
      target_type.map(x => List(("target_type", x))).getOrElse(List())

    ws.url(s"$endPoint/dir", timeout).withQueryString(params: _*).get().map(parse[List[FolderOrFile]])
  }

  def search(criteria: DocumentSearch, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, PaginatedSequence[Document]]] = {
    ws.url(s"$endPoint/search", timeout).withQueryString(LangUtils.productToQueryString(criteria): _*).get().map(parse[PaginatedSequence[Document]])
  }

  def delete(id: String, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, Document]] = {
    ws.url(s"$endPoint/$id", timeout).delete().map(parse[Document])
  }
}
