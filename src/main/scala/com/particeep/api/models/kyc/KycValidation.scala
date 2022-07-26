package com.particeep.api.models.kyc

import play.api.libs.json.{JsValue, Json}

case class KycValidation(
  is_valid:         Boolean = false,
  external_id:      String  = "",
  service_name:     String  = "",
  service_response: JsValue = Json.parse("{}")
)

object KycValidation {
  val format = Json.format[KycValidation]
}
