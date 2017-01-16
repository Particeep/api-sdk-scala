package com.particeep.api.models.form

import play.api.libs.json.Json

case class PossibilityEdition(
  id:     Option[String],
  label:  Option[String],
  index:  Option[Int],
  weight: Option[Int]
)

object PossibilityEdition {
  val format = Json.format[PossibilityEdition]
}
