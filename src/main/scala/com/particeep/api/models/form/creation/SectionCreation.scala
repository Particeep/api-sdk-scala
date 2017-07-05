package com.particeep.api.models.form.creation

import play.api.libs.json.Json

case class SectionCreation(
  form_id:     String,
  name:        Option[String],
  description: Option[String],
  index:       Option[Int]
)

object SectionCreation {
  val format = Json.format[SectionCreation]
}
