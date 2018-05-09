package com.particeep.api.models.form.get_deep

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import com.particeep.api.models.enums.QuestionType.QuestionType
import com.particeep.api.models.form.get.Possibility
import play.api.libs.json.Json

case class QuestionDeep(
  id:                 String                = "",
  created_at:         Option[ZonedDateTime] = None,
  section_id:         String                = "",
  label:              Option[String]        = None,
  description:        Option[String]        = None,
  possibility_id_dep: Option[String]        = None,
  question_type:      Option[QuestionType]  = None,
  required:           Boolean               = false,
  pattern:            Option[String]        = None,
  index:              Option[Int]           = None,
  possibilities:      Seq[Possibility]      = Seq(),

  //Only when getting a Form for a User
  answers: Option[Seq[String]] = None
)

object QuestionDeep {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  implicit val possibility_format = Possibility.format
  val format = Json.format[QuestionDeep]
}
