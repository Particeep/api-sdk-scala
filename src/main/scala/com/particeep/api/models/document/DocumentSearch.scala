package com.particeep.api.models.document

import play.api.libs.json.Json

/**
 * Created by Noe on 04/01/2017.
 */
case class DocumentSearch(
  owner_id:     Option[String],
  target_id:    Option[String],
  target_type:  Option[String],
  name:         Option[String],
  description:  Option[String],
  content_type: Option[String],
  external_id:  Option[String],
  limit:        Option[Int],
  offset:       Option[Int],
  sort_by:      Option[String],
  order_by:     Option[String] = Some("asc")
)

object DocumentSearch {
  val format = Json.format[DocumentSearch]
}
