package com.particeep.api.models.signature

import play.api.libs.json.Json

case class SignatureCreation(
  language:   Option[String] = None,
  fileUrl:    String,
  fileName:   String,
  firstName:  String,
  lastName:   String,
  email:      String,
  phone:      String,
  successURL: Option[String] = None,
  cancelURL:  Option[String] = None,
  failURL:    Option[String] = None
)

object SignatureCreation {
  val format = Json.format[SignatureCreation]
}
