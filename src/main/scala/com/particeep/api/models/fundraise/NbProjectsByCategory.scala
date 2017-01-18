package com.particeep.api.models.fundraise

import play.api.libs.json.Json

case class NbProjectsByCategory(
  activity_domain: Option[String],
  size:            Int
)

object NbProjectsByCategory {
  val format = Json.format[NbProjectsByCategory]
}
