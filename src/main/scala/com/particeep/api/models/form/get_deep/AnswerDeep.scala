package com.particeep.api.models.form.get_deep

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import play.api.libs.json.Json

case class AnswerDeep(
  id:          String                = "",
  created_at:  Option[ZonedDateTime] = None,
  user_id:     String                = "",
  question_id: String                = "",
  label:       Option[String]        = None
)

object AnswerDeep {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[AnswerDeep]
}
