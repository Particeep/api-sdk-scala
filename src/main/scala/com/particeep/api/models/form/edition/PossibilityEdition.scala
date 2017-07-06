package com.particeep.api.models.form.edition

import play.api.libs.json.Json

case class PossibilityEdition(
  label:  Option[String],
  index:  Option[Int],
  weight: Option[Int]
)

object PossibilityEdition {
  val format = Json.format[PossibilityEdition]
}
