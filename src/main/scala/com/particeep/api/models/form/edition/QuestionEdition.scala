package com.particeep.api.models.form.edition

import com.particeep.api.models.enums.QuestionType.QuestionType
import play.api.libs.json.Json

case class QuestionEdition(
  label:              Option[String],
  description:        Option[String]       = None,
  possibility_id_dep: Option[String]       = None,
  question_type:      Option[QuestionType],
  required:           Option[Boolean],
  pattern:            Option[String]       = None,
  index:              Option[Int]
)

object QuestionEdition {
  val format = Json.format[QuestionEdition]
}
