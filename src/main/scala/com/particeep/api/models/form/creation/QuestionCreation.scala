package com.particeep.api.models.form.creation

import com.particeep.api.models.enums.QuestionType.QuestionType
import play.api.libs.json.Json

case class QuestionCreation(
  section_id:    String,
  label:         Option[String]       = None,
  description:   Option[String]       = None,
  question_type: Option[QuestionType] = None,
  required:      Option[Boolean]      = None,
  pattern:       Option[String]       = None,
  index:         Option[Int]          = None
)

object QuestionCreation {
  val format = Json.format[QuestionCreation]
}
