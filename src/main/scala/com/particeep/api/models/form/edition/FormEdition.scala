package com.particeep.api.models.form.edition

import play.api.libs.json.Json

case class FormEdition(
  name:        Option[String] = None,
  description: Option[String] = None,
  tag:         Option[String] = None
)

object FormEdition {
  val format = Json.format[FormEdition]
}
