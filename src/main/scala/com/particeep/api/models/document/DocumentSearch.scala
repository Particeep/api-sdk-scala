package com.particeep.api.models.document

import play.api.libs.json.Json

/**
 * Created by Noe on 04/01/2017.
 */
case class DocumentSearch(
  owner_id:     Option[String] = None,
  target_id:    Option[String] = None,
  target_type:  Option[String] = None,
  path:         Option[String] = None,
  name:         Option[String] = None,
  description:  Option[String] = None,
  content_type: Option[String] = None,
  external_id:  Option[String] = None,
  limit:        Option[Int]    = None,
  offset:       Option[Int]    = None,
  sort_by:      Option[String] = None,
  order_by:     Option[String] = Some("asc")
)

object DocumentSearch {
  val format = Json.format[DocumentSearch]
}
