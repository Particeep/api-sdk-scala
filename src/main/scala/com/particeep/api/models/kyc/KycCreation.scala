package com.particeep.api.models.kyc

import com.particeep.api.models.enums.UserType.{ NATURAL, UserType }
import play.api.libs.json.Json

case class KycCreation(
    owner_id:   String         = "",
    owner_type: String         = "",
    user_type:  UserType       = NATURAL,
    owner_ip:   Option[String] = None
)

object KycCreation {
  val format = Json.format[KycCreation]
}
