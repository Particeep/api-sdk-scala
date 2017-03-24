package com.particeep.api.models.webhook

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import play.api.libs.json.Json

case class WebHook(
  id:         String                = "",
  created_at: Option[ZonedDateTime] = None,
  name:       String                = "",
  url:        String                = ""
)

object WebHook {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[WebHook]
}
