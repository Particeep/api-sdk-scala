package com.particeep.api.models.form.creation

import play.api.libs.json.Json
import play.api.libs.json.JsObject

case class SectionCreation(
  form_id:     String,
  name:        Option[String]   = None,
  description: Option[String]   = None,
  index:       Option[Int]      = None,
  tag:         Option[String]   = None,
  custom:      Option[JsObject] = None
)

object SectionCreation {
  val format = Json.format[SectionCreation]
}
