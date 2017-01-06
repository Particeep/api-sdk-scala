package com.particeep.api.models.kyc

import com.particeep.api.models.enums.KycType.KycType
import play.api.libs.json.Json

case class KycEdition(
  doc_type: KycType,
  urls:     Seq[String]
)

object KycEdition {
  val format = Json.format[KycEdition]
}

case class KycsEdition(
  owner_id:   String,
  owner_type: String,
  docs:       Seq[KycEdition]
)

object KycsEdition {
  implicit val kyc_format = KycEdition.format

  val format = Json.format[KycsEdition]
}
