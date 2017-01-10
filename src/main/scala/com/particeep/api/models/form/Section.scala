package com.particeep.api.models.form

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import play.api.libs.json.Json

case class Section(
  id:          String                = "",
  created_at:  Option[ZonedDateTime] = None,
  form_id:     String                = "",
  name:        Option[String]        = None,
  description: Option[String]        = None,
  index:       Option[Int]           = None,
  questions:   Seq[Question]         = Seq()
)

object Section {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  implicit val question_format = Question.format
  val format = Json.format[Section]
}
