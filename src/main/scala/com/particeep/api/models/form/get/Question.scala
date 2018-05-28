package com.particeep.api.models.form.get

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import com.particeep.api.models.enums.QuestionType.QuestionType
import play.api.libs.json.Json

case class Question(
  id:                 String                = "",
  created_at:         Option[ZonedDateTime] = None,
  section_id:         String                = "",
  label:              Option[String]        = None,
  description:        Option[String]        = None,
  possibility_id_dep: Option[String]        = None,
  question_type:      Option[QuestionType]  = None,
  pattern:            Option[String]        = None,
  required:           Boolean               = false,
  index:              Option[Int]           = None
)

object Question {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[Question]
}
