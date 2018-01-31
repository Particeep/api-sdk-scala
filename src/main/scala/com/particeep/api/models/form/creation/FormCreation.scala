package com.particeep.api.models.form.creation

import play.api.libs.json.Json
import play.api.libs.json.JsObject

case class FormCreation(
  name:        Option[String],
  description: Option[String]   = None,
  tag:         Option[String]   = None,
  custom:      Option[JsObject] = None
)

object FormCreation {
  val format = Json.format[FormCreation]
}
