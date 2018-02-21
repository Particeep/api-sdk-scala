package com.particeep.api.models.signature

import play.api.libs.json.{ JsObject, Json }

case class SignatureMultipleCreation(
  language:   Option[String]       = None,
  fileUrl:    String,
  fileName:   String,
  signers:    Seq[SignatureSigner],
  successURL: Option[String]       = None,
  cancelURL:  Option[String]       = None,
  failURL:    Option[String]       = None,
  tag:        Option[String]       = None,
  custom:     Option[JsObject]     = None
)

object SignatureMultipleCreation {
  private[this] implicit val signatureSignerFormat = SignatureSigner.format
  val format = Json.format[SignatureMultipleCreation]
}
