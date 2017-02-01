package com.particeep.api.models.message

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import play.api.libs.json.Json

case class Message(
                  id: String = "",
                  created_at: Option[ZonedDateTime] = None,
                  title: Option[String] = None,
                  body: Option[String] = None,
                  link: Option[String] = None,
                  author_id: String = "",
                  target_id: String = "",
                  target_type: String = "",
                  isRead: Boolean = false
                  )

object Message {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[Message]
}
