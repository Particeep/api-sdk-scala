package com.particeep.api.models.enterpise

import play.api.libs.json.Json

case class NbProjectsByActivity(
  activity_domain: Option[String],
  size:            Int
)

object NbProjectsByActivity {
  val format = Json.format[NbProjectsByActivity]
}
