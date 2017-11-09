package com.particeep.api.models.document

import play.api.libs.json.{ JsObject, Json }

/**
 * Created by Noe on 04/01/2017.
 */
case class DocumentCreation(
  target_id:              Option[String]   = None,
  target_type:            Option[String]   = None,
  description:            Option[String]   = None,
  name:                   Option[String]   = None,
  path:                   Option[String]   = None,
  locked:                 Option[Boolean]  = None,
  override_existing_file: Option[Boolean]  = None,
  tag:                    Option[String]   = None,
  custom:                 Option[JsObject] = None
)

object DocumentCreation {
  val format = Json.format[DocumentCreation]
}
