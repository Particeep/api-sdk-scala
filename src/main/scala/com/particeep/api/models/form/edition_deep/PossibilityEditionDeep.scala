package com.particeep.api.models.form.edition_deep

import play.api.libs.json.Json

case class PossibilityEditionDeep(
  id:     Option[String],
  label:  Option[String],
  index:  Option[Int],
  weight: Option[Int]
)

object PossibilityEditionDeep {
  val format = Json.format[PossibilityEditionDeep]
}
