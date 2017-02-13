package com.particeep.api.models.enterprise

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import play.api.libs.json.Json

case class ManagerLink(
  id:         Option[String]        = None,
  created_at: Option[ZonedDateTime] = None,
  manager_id: String                = "",
  name:       Option[String]        = None,
  tag:        Option[String]        = None
)

object ManagerLink {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[ManagerLink]
}
