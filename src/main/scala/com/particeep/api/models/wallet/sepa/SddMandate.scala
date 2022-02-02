package com.particeep.api.models.wallet.sepa

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import com.particeep.api.models.enums.SddMandateStatus.{ PENDING, SddMandateStatus }
import play.api.libs.json.Json

case class SddMandate(
    id:         String                = "",
    created_at: Option[ZonedDateTime] = None,
    holder:     String                = "",
    bic:        String                = "",
    iban:       String                = "",
    is_b2b:     Boolean               = true,
    street:     String                = "",
    zip:        String                = "",
    city:       String                = "",
    country:    String                = "",
    wallet_id:  String                = "",
    status:     SddMandateStatus      = PENDING,
    tag:        Option[String]        = None
)

object SddMandate {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[SddMandate]
}
