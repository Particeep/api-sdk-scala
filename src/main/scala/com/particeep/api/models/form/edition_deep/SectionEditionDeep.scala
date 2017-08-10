package com.particeep.api.models.form.edition_deep

import play.api.libs.json.Json

case class SectionEditionDeep(
  id:          Option[String],
  name:        Option[String],
  description: Option[String],
  index:       Option[Int],
  questions:   Option[Seq[QuestionEditionDeep]]
)

object SectionEditionDeep {
  implicit val question_edition_format = QuestionEditionDeep.format
  val format = Json.format[SectionEditionDeep]
}
