package com.particeep.api.models.message

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import play.api.libs.json.Json

case class Conversation(
                       firstName: Option[String] = None,
                       lastName: Option[String] = None,
                       avatarUrl: Option[String] = None,
                       lastUpdate: ZonedDateTime = ZonedDateTime.now(),
                       isRead: Boolean = false
                       )

object Conversation {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[Conversation]
}
