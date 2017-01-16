package com.particeep.api.models.document_generation

import play.api.libs.json.Json

case class DocumentGeneration(
  url:    String,
  params: Map[String, String]
)

object DocumentGeneration {
  val format = Json.format[DocumentGeneration]
}
