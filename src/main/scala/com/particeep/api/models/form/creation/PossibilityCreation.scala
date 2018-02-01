package com.particeep.api.models.form.creation

import play.api.libs.json.Json

case class PossibilityCreation(
  question_id: String,
  label:       Option[String] = None,
  index:       Option[Int]    = None,
  weight:      Option[Int]    = None
)

object PossibilityCreation {
  val format = Json.format[PossibilityCreation]
}
