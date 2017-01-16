package com.particeep.api.models.form

import play.api.libs.json.Json

case class SectionEdition(
  id:          Option[String],
  name:        Option[String],
  description: Option[String],
  index:       Option[Int],
  questions:   Option[Seq[QuestionEdition]]
)

object SectionEdition {
  implicit val question_edition_format = QuestionEdition.format
  val format = Json.format[SectionEdition]
}
