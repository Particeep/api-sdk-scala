package com.particeep.api.models.news

import play.api.libs.json.{ JsObject, Json }

case class NewsEdition(
    title:       Option[String]   = None,
    message:     Option[String]   = None,
    img_url:     Option[String]   = None,
    content_url: Option[String]   = None,
    is_report:   Option[Boolean]  = None,
    tag:         Option[String]   = None,
    custom:      Option[JsObject] = None
)

object NewsEdition {
  val format = Json.format[NewsEdition]
}
