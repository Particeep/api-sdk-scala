package com.particeep.api.models.form.creation

import play.api.libs.json.Json

case class PossibilityCreation(
  question_id: String,
  label:       Option[String],
  index:       Option[Int],
  weight:      Option[Int]
)

object PossibilityCreation {
  val format = Json.format[PossibilityCreation]
}
