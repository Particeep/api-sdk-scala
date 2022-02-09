package com.particeep.api.models.kpi

import java.time.ZonedDateTime
import play.api.libs.json.Json
import com.particeep.api.core.Formatter

case class KpiValueCreation(
    date:    ZonedDateTime,
    value:   Double,
    comment: Option[String] = None,
    tag:     Option[String] = None
)

object KpiValueCreation {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  implicit val format = Json.format[KpiValueCreation]
}
