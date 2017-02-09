package com.particeep.api.models.kyc

import com.particeep.api.models.enums.KycStatus.{ CREATED, KycStatus }
import play.api.libs.json.Json

case class KycGroup(
  owner_id:   String    = "",
  owner_type: String    = "",
  status:     KycStatus = CREATED,
  docs:       Seq[Kyc]  = Seq()
)

object KycGroup {
  implicit val kyc_format = Kyc.format

  val format = Json.format[KycGroup]
}
