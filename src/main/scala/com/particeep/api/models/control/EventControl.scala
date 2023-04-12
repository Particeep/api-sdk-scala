package com.particeep.api.models.control

import play.api.libs.json.{ JsValue, Json, OFormat }

final case class EventControl(
    id:         String,
    name:       String,
    created_at: Long,
    created_by: String,
    entity_id:  String,
    payload:    JsValue,
    tags:       Set[String]
)

object EventControl {
  implicit val eventFormat: OFormat[EventControl] = Json.format[EventControl]
}