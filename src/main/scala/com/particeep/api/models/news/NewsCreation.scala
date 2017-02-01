package com.particeep.api.models.news

import play.api.libs.json.Json

case class NewsCreation(
  target_id:   String,
  target_type: String,
  author_id:   String,
  title:       Option[String] = None,
  message:     Option[String] = None,
  img_url:     Option[String] = None,
  content_url: Option[String] = None,
  tags:        Option[String] = None
)

object NewsCreation {
  val format = Json.format[NewsCreation]
}
