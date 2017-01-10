package com.particeep.api.models.form

import play.api.libs.json.Json

case class FormEdition(
  name:        Option[String],
  description: Option[String],
  tag:         Option[String],
  sections:    Option[Seq[SectionEdition]]
)

object FormEdition {
  implicit val section_edition_format = SectionEdition.format
  val format = Json.format[FormEdition]
}
