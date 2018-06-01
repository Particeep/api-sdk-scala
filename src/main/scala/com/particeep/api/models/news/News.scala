package com.particeep.api.models.news

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import play.api.libs.json.{ JsObject, Json }

case class News(
  id:          String                = "",
  deleted_at:  Option[ZonedDateTime] = None,
  target_id:   String                = "",
  target_type: String                = "",
  author_id:   String                = "",
  publish_at:  ZonedDateTime         = ZonedDateTime.now(),
  title:       Option[String]        = None,
  message:     Option[String]        = None,
  img_url:     Option[String]        = None,
  content_url: Option[String]        = None,
  is_report:   Option[Boolean]       = None,
  tag:         Option[String]        = None,
  custom:      Option[JsObject]      = None
)

object News {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[News]
}
