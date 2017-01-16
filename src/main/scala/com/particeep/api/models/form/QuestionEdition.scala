package com.particeep.api.models.form

import com.particeep.api.models.enums.QuestionType.QuestionType
import play.api.libs.json.Json

case class QuestionEdition(
  id:            Option[String],
  label:         Option[String],
  question_type: Option[QuestionType],
  required:      Option[Boolean],
  index:         Option[Int],
  possibilities: Option[Seq[PossibilityEdition]]
)

object QuestionEdition {
  implicit val possibility_edition_format = PossibilityEdition.format
  val format = Json.format[QuestionEdition]
}
