package com.particeep.api.models.form.creation

import com.particeep.api.models.enums.QuestionType.QuestionType
import play.api.libs.json.Json

case class QuestionCreation(
  section_id:    String,
  label:         Option[String],
  question_type: Option[QuestionType],
  required:      Option[Boolean],
  pattern:       Option[String]       = None,
  index:         Option[Int]
)

object QuestionCreation {
  val format = Json.format[QuestionCreation]
}
