package com.particeep.api

import com.ning.http.client.{AsyncHttpClient, ListenableFuture, Response}
import com.ning.http.client.multipart.{FilePart, StringPart}
import com.particeep.api.core.{ResponseParser, WSClient}
import com.particeep.api.models.document.{Document, DocumentCreation, DocumentSearch}
import com.particeep.api.models.{ErrorResult, PaginatedSequence}
import com.particeep.api.utils.LangUtils
import play.api.Play.current
import play.api.libs.Files.TemporaryFile
import play.api.libs.ws.WS
import play.api.mvc.MultipartFormData

import scala.concurrent.{ExecutionContext, Future}

/**
 * Created by Noe on 27/12/2016.
 */

trait DocumentCapability {
  self: WSClient =>

  val document: DocumentClient = new DocumentClient(this)
}

class DocumentClient(ws: WSClient) extends ResponseParser {

  private val endPoint: String = "/document"
  implicit val format = Document.format
  implicit val format_creation = DocumentCreation.format
  implicit val format_search = DocumentSearch.format

  def upload(owner_id: String, file: MultipartFormData[TemporaryFile], document: DocumentCreation)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Document]] = {
    val url = s"$endPoint/$owner_id/upload"
    Future { prepareRequest(url, file, document).get() }.map(parse[Document])
  }

  private def prepareRequest(url: String, file: MultipartFormData[TemporaryFile], document: DocumentCreation)(implicit exec: ExecutionContext): ListenableFuture[Response] = {
    val documentFilePart = file.files.head
    val client = WS.client.underlying[AsyncHttpClient]
    val postBuilder = ws.urlFileUpload(url, client)
    val builder = postBuilder.addBodyPart(
      new FilePart("document", documentFilePart.ref.file, documentFilePart.contentType.getOrElse("application/octet-stream"))
    )
    addDocumentApiToBody(postBuilder, document)
    client.executeRequest(builder.build())
  }

  private def addDocumentApiToBody(builder: AsyncHttpClient#BoundRequestBuilder, document: DocumentCreation) {
    builder.addBodyPart(new StringPart("target_id", document.target_id))
      .addBodyPart(new StringPart("target_type", document.target_type))
      .addBodyPart(new StringPart("description", document.description.getOrElse("")))
      .addBodyPart(new StringPart("name", document.name.getOrElse("")))
      .addBodyPart(new StringPart("path", document.path.getOrElse("")))
      .addBodyPart(new StringPart("locked", document.locked.toString))
      .addBodyPart(new StringPart("override_existing_file", document.override_existing_file.getOrElse(false).toString))
  }

  def byId(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Document]] = {
    ws.url(s"$endPoint/$id", timeout).get().map(parse[Document])
  }

  def listDir(path: String, target_id: Option[String], target_type: Option[String], timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Document]] = {
    val params: List[(String, String)] = List(("path", path)) ++
      target_id.map(x => List(("target_id", x))).getOrElse(List()) ++
      target_type.map(x => List(("target_type", x))).getOrElse(List())

    ws.url(s"$endPoint/dir", timeout).withQueryString(params: _*).get().map(parse[Document])
  }

  def search(criteria: DocumentSearch, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[Document]]] = {
    ws.url(s"$endPoint/search", timeout).withQueryString(LangUtils.productToQueryString(criteria): _*).get().map(parse[PaginatedSequence[Document]])
  }

  def delete(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Document]] = {
    ws.url(s"$endPoint/$id", timeout).delete().map(parse[Document])
  }
}
