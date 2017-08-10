package com.particeep.api.models.form.get_deep

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import play.api.libs.json.Json

case class SectionDeep(
  id:          String                = "",
  created_at:  Option[ZonedDateTime] = None,
  form_id:     String                = "",
  name:        Option[String]        = None,
  description: Option[String]        = None,
  index:       Option[Int]           = None,
  questions:   Seq[QuestionDeep]     = Seq(),

  //Only when getting a Form for a User
  done: Option[Boolean] = None
)

object SectionDeep {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  implicit val question_format = QuestionDeep.format
  val format = Json.format[SectionDeep]
}
