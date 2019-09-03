package com.particeep.api.models.signature

import play.api.libs.json.{ JsObject, Json }

case class SignatureCreation(
  language:       Option[String]   = None,
  fileUrl:        String,
  fileName:       String,
  firstName:      String,
  lastName:       String,
  email:          String,
  phone:          String,
  successURL:     Option[String]   = None,
  cancelURL:      Option[String]   = None,
  failURL:        Option[String]   = None,
  target_id:      Option[String]   = None,
  target_type:    Option[String]   = None,
  signature_type: Option[String]   = None,
  tag:            Option[String]   = None,
  custom:         Option[JsObject] = None
)

object SignatureCreation {
  val format = Json.format[SignatureCreation]
}
