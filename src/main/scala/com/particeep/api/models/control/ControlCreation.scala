package com.particeep.api.models.control

import play.api.libs.json.{ JsObject, Json, OFormat }

final case class ControlCreation(
    target_id:     String,
    target_type:   String,
    target_entity: JsObject
)

object ControlCreation {
  implicit val control_creation_format: OFormat[ControlCreation] = Json.format[ControlCreation]
}