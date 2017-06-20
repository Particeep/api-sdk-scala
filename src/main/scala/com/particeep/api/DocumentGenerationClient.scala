package com.particeep.api

import com.particeep.api.core._
import com.particeep.api.models.ErrorResult
import com.particeep.api.models.document.Document
import com.particeep.api.models.document_generation.{ DocumentGeneration, DocumentGenerationAndUpload }
import play.api.libs.json.Json

import scala.concurrent.{ ExecutionContext, Future }

trait DocumentGenerationCapability {
  self: WSClient =>

  val document_generation: DocumentGenerationClient = new DocumentGenerationClient(this)

  def document_generation(credentials: ApiCredential): DocumentGenerationClient = new DocumentGenerationClient(this, Some(credentials))
}

object DocumentGenerationClient {
  private val endPoint: String = "/document-generation"
  private implicit val format_generation = DocumentGeneration.format
  private implicit val format_generation_and_upload = DocumentGenerationAndUpload.format
  private implicit val format_document = Document.format
}

class DocumentGenerationClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends ResponseParser with WithWS with WithCredentials with EntityClient {

  import DocumentGenerationClient._

  def generation(document_generation: DocumentGeneration, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Stream[Byte]]] = {
    ws.url(s"$endPoint", timeout).post(Json.toJson(document_generation)).map(parse[Stream[Byte]])
  }

  def generationAndUpload(document_generation: DocumentGenerationAndUpload, owner_id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Document]] = {
    ws.url(s"$endPoint/upload/$owner_id", timeout).post(Json.toJson(document_generation)).map(parse[Document])
  }
}
