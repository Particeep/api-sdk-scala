package com.particeep.api.models.form.get_deep

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import com.particeep.api.models.enums.QuestionType.QuestionType
import play.api.libs.json.Json

case class QuestionDeep(
  id:            String                = "",
  created_at:    Option[ZonedDateTime] = None,
  section_id:    String                = "",
  label:         Option[String]        = None,
  question_type: Option[QuestionType]  = None,
  required:      Boolean               = false,
  index:         Option[Int]           = None,
  possibilities: Seq[PossibilityDeep]  = Seq(),

  //Only when getting a Form for a User
  answers: Option[Seq[String]] = None
)

object QuestionDeep {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  implicit val possibility_format = PossibilityDeep.format
  val format = Json.format[QuestionDeep]
}
