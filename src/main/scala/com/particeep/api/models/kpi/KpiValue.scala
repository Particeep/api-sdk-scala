package com.particeep.api.models.kpi

import java.time.ZonedDateTime
import play.api.libs.json.Json
import com.particeep.api.core.Formatter

case class KpiValue(
    id:         String                = "",
    created_at: Option[ZonedDateTime] = None,
    update_at:  Option[ZonedDateTime] = None,
    date:       ZonedDateTime         = ZonedDateTime.now,
    value:      Double                = 0d,
    kpi_id:     Option[String]        = None,
    comment:    Option[String]        = None,
    tag:        Option[String]        = None
)

object KpiValue {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  implicit val format = Json.format[KpiValue]
}
