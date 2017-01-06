package com.particeep.api.models.kyc

import com.particeep.api.models.enums.UserType.UserType
import play.api.libs.json.Json

case class KycCreation(
  owner_id:   String,
  owner_type: String,
  user_type:  UserType
)

object KycCreation {
  val format = Json.format[KycCreation]
}
