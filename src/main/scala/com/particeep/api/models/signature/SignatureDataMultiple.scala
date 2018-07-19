package com.particeep.api.models.signature

import play.api.libs.json.Json

case class SignatureDataMultiple(
  id:             String,
  status:         String,
  current_signer: Int,
  signatures:     Seq[SignatureData]
)

object SignatureDataMultiple {
  private[this] implicit val signatureDataFormat = SignatureData.format
  implicit val format = Json.format[SignatureDataMultiple]
}
