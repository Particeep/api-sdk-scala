package com.particeep.api.models.form

import play.api.libs.json.Json

case class FormCreation(
  name:        Option[String],
  description: Option[String],
  tag:         Option[String]
)

object FormCreation {
  val format = Json.format[FormCreation]
}
