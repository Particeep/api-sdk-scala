package com.particeep.api.models.document

import play.api.libs.json.Json

/**
 * Created by Noe on 04/01/2017.
 */
case class Document(
  id:           String         = "",
  owner_id:     String         = "",
  target_id:    Option[String] = None,
  target_type:  Option[String] = None,
  name:         String         = "",
  description:  Option[String] = None,
  content_type: Option[String] = None,
  path:         String         = "",
  doc_type:     String         = "",
  locked:       Boolean        = false
)

object Document {
  implicit val format = Json.format[Document]
}
