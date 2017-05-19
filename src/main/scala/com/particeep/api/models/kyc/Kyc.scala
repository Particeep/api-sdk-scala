package com.particeep.api.models.kyc

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import com.particeep.api.models.enums.KycType.{ ID_CARD, KycType }
import play.api.libs.json.Json

case class Kyc(
  doc_type:        KycType               = ID_CARD,
  created_at:      Option[ZonedDateTime] = None,
  update_at:       Option[ZonedDateTime] = None,
  refusal_reason:  Option[String]        = None,
  refusal_message: Option[String]        = None,
  urls:            Seq[String]           = Seq()
)

object Kyc {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[Kyc]
}
