package com.particeep.api.models.form.edition

import play.api.libs.json.Json

case class SectionEdition(
  name:        Option[String],
  description: Option[String],
  index:       Option[Int]
)

object SectionEdition {
  val format = Json.format[SectionEdition]
}
