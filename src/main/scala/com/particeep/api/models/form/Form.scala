package com.particeep.api.models.form

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import play.api.libs.json.Json

case class Form(
  id:              String                = "",
  created_at:      Option[ZonedDateTime] = None,
  name:            Option[String]        = None,
  description:     Option[String]        = None,
  tag:             Option[String]        = None,
  last_updated_at: Option[ZonedDateTime] = None,
  sections:        Seq[Section]          = Seq()
)

object Form {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  implicit val section_format = Section.format
  val format = Json.format[Form]
}
