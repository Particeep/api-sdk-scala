package com.particeep.api

import com.particeep.api.core._
import com.particeep.api.models.ErrorResult
import com.particeep.api.models.document.Document
import com.particeep.api.models.document_generation.{ DocumentGeneration, DocumentGenerationAndUpload }
import play.api.libs.iteratee.Enumerator
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

class DocumentGenerationClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends WithWS with WithCredentials with EntityClient {

  import DocumentGenerationClient._

  def generation(document_generation: DocumentGeneration, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Enumerator[Array[Byte]]]] = {
    ws.postStream(s"$endPoint", timeout, Json.toJson(document_generation))
  }

  def generationAndUpload(document_generation: DocumentGenerationAndUpload, owner_id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Document]] = {
    ws.post[Document](s"$endPoint/upload/$owner_id", timeout, Json.toJson(document_generation))
  }
}
