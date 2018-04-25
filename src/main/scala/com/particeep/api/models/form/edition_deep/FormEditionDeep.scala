package com.particeep.api.models.form.edition_deep

import play.api.libs.json.Json

case class FormEditionDeep(
  name:        Option[String],
  description: Option[String],
  target_id:   Option[String],
  target_type: Option[String],
  tag:         Option[String],
  sections:    Option[Seq[SectionEditionDeep]]
)

object FormEditionDeep {
  implicit val section_edition_format = SectionEditionDeep.format
  val format = Json.format[FormEditionDeep]
}
