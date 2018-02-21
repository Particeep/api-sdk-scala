package com.particeep.api.models.signature

import play.api.libs.json.Json

case class SignatureSigner(
  firstName: String,
  lastName:  String,
  email:     String,
  phone:     String
)

object SignatureSigner {
  val format = Json.format[SignatureSigner]
}
