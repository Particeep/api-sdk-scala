package com.particeep.api.models.kyc

import play.api.libs.json.Json

case class KycAskValidation(
 url: String
)

object KycAskValidation {
  val format = Json.format[KycAskValidation]
}
