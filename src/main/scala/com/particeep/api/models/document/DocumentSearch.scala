package com.particeep.api.models.document

import play.api.libs.json.Json

/**
 * Created by Noe on 04/01/2017.
 */
case class DocumentSearch(
  owner_id:     Option[String],
  target_id:    Option[String],
  target_type:  Option[String],
  path:         Option[String],
  name:         Option[String],
  description:  Option[String],
  content_type: Option[String],
  external_id:  Option[String],
  doc_type:     Option[String],
  custom:       Option[String]
)

object DocumentSearch {
  val format = Json.format[DocumentSearch]
}
