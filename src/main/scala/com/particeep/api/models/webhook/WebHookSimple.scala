package com.particeep.api.models.webhook

import play.api.libs.json.Json

case class WebHookSimple(
  name: String,
  url:  String
)

object WebHookSimple {
  val format = Json.format[WebHookSimple]
}
