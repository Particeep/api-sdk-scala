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
  doc_type:     Option[String] = None,
  custom:       Option[String] = None
)

object DocumentSearch {
  val format = Json.format[DocumentSearch]
}
