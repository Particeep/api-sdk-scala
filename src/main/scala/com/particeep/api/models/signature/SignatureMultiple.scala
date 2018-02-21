package com.particeep.api.models.signature

import com.particeep.api.models.enums.SignatureStatus.SignatureStatus
import play.api.libs.json.Json

case class SignatureMultiple(
  id:             String,
  status:         SignatureStatus,
  current_signer: Int,
  signatures:     Seq[Signature]
)

object SignatureMultiple {
  private[this] implicit val signatureFormat = Signature.format
  implicit val format = Json.format[SignatureMultiple]
}
