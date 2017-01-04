package com.particeep.api.models.document

import play.api.libs.json.Json

/**
 * Created by Noe on 04/01/2017.
 */
case class DocumentCreation(
  target_id:              String,
  target_type:            String,
  description:            Option[String],
  name:                   Option[String],
  path:                   Option[String],
  locked:                 Option[Boolean],
  override_existing_file: Option[Boolean]
)

object DocumentCreation {
  val format = Json.format[DocumentCreation]
}
