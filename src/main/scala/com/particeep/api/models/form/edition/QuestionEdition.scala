package com.particeep.api.models.form.edition

import com.particeep.api.models.enums.QuestionType.QuestionType
import play.api.libs.json.Json

case class QuestionEdition(
  label:         Option[String],
  question_type: Option[QuestionType],
  required:      Option[Boolean],
  index:         Option[Int]
)

object QuestionEdition {
  val format = Json.format[QuestionEdition]
}
