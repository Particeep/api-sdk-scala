package com.particeep.api.models.form

import play.api.libs.json.Json

case class AnswerCreation(
  question_id: String,
  answer:      Seq[String]
)

object AnswerCreation {
  val format = Json.format[AnswerCreation]
}
