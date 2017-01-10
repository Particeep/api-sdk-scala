package com.particeep.api.models.form

import com.particeep.api.models.enums.QuestionType.QuestionType
import play.api.libs.json.Json

case class AnswerCreation(
  question_id:           String,
  question_type:         Option[QuestionType],
  answer:                Option[String],
  checked_possibilities: Option[Seq[String]]
)

object AnswerCreation {
  val format = Json.format[AnswerCreation]
}
