package com.particeep.api.models.news

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import play.api.libs.json.{ JsObject, Json }

case class NewsCreation(
  target_id:   String,
  target_type: String,
  author_id:   String,
  publish_at:  ZonedDateTime,
  title:       Option[String]   = None,
  message:     Option[String]   = None,
  img_url:     Option[String]   = None,
  content_url: Option[String]   = None,
  tag:         Option[String]   = None,
  custom:      Option[JsObject] = None
)

object NewsCreation {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[NewsCreation]
}
