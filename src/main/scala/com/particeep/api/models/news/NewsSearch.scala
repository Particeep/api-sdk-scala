package com.particeep.api.models.news

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import play.api.libs.json.Json

case class NewsSearch(
  target_id:      Option[String]        = None,
  target_type:    Option[String]        = None,
  author_id:      Option[String]        = None,
  title:          Option[String]        = None,
  message:        Option[String]        = None,
  is_report:      Option[Boolean]       = None,
  tag:            Option[String]        = None,
  publish_before: Option[ZonedDateTime] = None,
  publish_after:  Option[ZonedDateTime] = None
)

object NewsSearch {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[NewsSearch]
}
