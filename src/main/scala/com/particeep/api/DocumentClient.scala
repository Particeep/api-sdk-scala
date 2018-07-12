package com.particeep.api

import java.io.File

import akka.stream.scaladsl.Source
import akka.util.ByteString
import com.ning.http.client.multipart.StringPart
import com.particeep.api.core._
import com.particeep.api.models.document._
import com.particeep.api.models.{ ErrorResult, PaginatedSequence, TableSearch }
import com.particeep.api.utils.LangUtils
import play.api.libs.json.Json

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

class DocumentClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends WithWS with WithCredentials with EntityClient {

  import DocumentClient._

  def upload(
    owner_id:          String,
    file:              File,
    content_type:      String,
    document_creation: DocumentCreation,
    timeout:           Long             = defaultTimeOut
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
    ws.postFile[Document](s"$endPoint/$owner_id/upload", timeout, file, content_type, bodyParts)
  }

  def createDir(owner_id: String, document_creation: DocumentCreation, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Document]] = {
    ws.post[Document](s"$endPoint/$owner_id/dir", timeout, Json.toJson(document_creation))
  }

  def download(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Source[ByteString, _]]] = {
    ws.getStream(s"$endPoint/download/$id", timeout)
  }

  def byId(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Document]] = {
    ws.get[Document](s"$endPoint/$id", timeout)
  }

  def byIds(ids: Seq[String], timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[Document]]] = {
    ws.get[List[Document]](s"$endPoint", timeout, List("ids" -> ids.mkString(",")))
  }

  def update(id: String, document_edition: DocumentEdition, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Document]] = {
    ws.post[Document](s"$endPoint/$id", timeout, Json.toJson(document_edition))
  }

  def listDir(
    path:        String,
    target_id:   Option[String],
    target_type: Option[String],
    timeout:     Long           = defaultTimeOut
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[FolderOrFile]]] = {
    val params: List[(String, String)] = List(("path", path)) ++
      target_id.map(x => List(("target_id", x))).getOrElse(List()) ++
      target_type.map(x => List(("target_type", x))).getOrElse(List())

    ws.get[List[FolderOrFile]](s"$endPoint/dir", timeout, params)
  }

  def search(criteria: DocumentSearch, table_criteria: TableSearch, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[Document]]] = {
    ws.get[PaginatedSequence[Document]](s"$endPoint/search", timeout, LangUtils.productToQueryString(criteria) ++ LangUtils.productToQueryString(table_criteria))
  }

  def delete(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Document]] = {
    ws.delete[Document](s"$endPoint/$id", timeout)
  }
}
