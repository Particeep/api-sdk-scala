package com.particeep.api.models.kpi

import java.time.ZonedDateTime
import play.api.libs.json.Json
import com.particeep.api.core.Formatter

case class KpiValueUpdate(
    id:    String,
    date:  ZonedDateTime,
    value: Double
)

object KpiValueUpdate {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  implicit val format = Json.format[KpiValueUpdate]
}
