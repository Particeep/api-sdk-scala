package com.particeep.api.models.form

import java.time.ZonedDateTime

import play.api.libs.json.Json

case class Answer(
  id:          String                = "",
  created_at:  Option[ZonedDateTime] = None,
  user_id:     String                = "",
  question_id: String                = "",
  label:       Option[String]        = None
)

object Answer {
  val format = Json.format[Answer]
}
