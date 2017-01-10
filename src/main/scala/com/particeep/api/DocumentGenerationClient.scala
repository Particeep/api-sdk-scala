package com.particeep.api

import com.particeep.api.core.{ResponseParser, WSClient}
import com.particeep.api.models.ErrorResult
import com.particeep.api.models.document_generation.DocumentGeneration
import play.api.libs.json.Json

import scala.concurrent.{ExecutionContext, Future}

trait DocumentGenerationCapability {
  self: WSClient =>

  val document_generation: DocumentGenerationClient = new DocumentGenerationClient(this)
}

class DocumentGenerationClient(ws: WSClient) extends ResponseParser {

  private val endPoint: String = "/document-generation"
  implicit val format_generation = DocumentGeneration.format

  def generation(document_generation: DocumentGeneration, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Array[Byte]]] = {
    ws.url(s"$endPoint", timeout).post(Json.toJson(document_generation)).map(parse[Array[Byte]])
  }
}
