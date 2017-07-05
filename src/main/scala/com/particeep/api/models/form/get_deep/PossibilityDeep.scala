package com.particeep.api.models.form.get_deep

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import play.api.libs.json.Json

case class PossibilityDeep(
  id:          String                = "",
  created_at:  Option[ZonedDateTime] = None,
  question_id: String                = "",
  label:       Option[String]        = None,
  index:       Option[Int]           = None,
  weight:      Option[Int]           = None
)

object PossibilityDeep {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[PossibilityDeep]
}
