package com.particeep.api.models.document

import play.api.libs.json.{ JsObject, Json }

/**
 * Created by Noe on 17/02/2017.
 */
case class DocumentEdition(
  name:        Option[String],
  description: Option[String],
  tag:         Option[String]   = None,
  custom:      Option[JsObject] = None
)

object DocumentEdition {
  val format = Json.format[DocumentEdition]
}
