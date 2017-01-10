package com.particeep.api.models.form

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import play.api.libs.json.Json

case class Possibility(
  id:          String                = "",
  created_at:  Option[ZonedDateTime] = None,
  question_id: String                = "",
  label:       Option[String]        = None,
  index:       Option[Int]           = None,
  weight:      Option[Int]           = None,

  //Only when getting a Form for a User
  checked: Option[Boolean] = None
)

object Possibility {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[Possibility]
}
