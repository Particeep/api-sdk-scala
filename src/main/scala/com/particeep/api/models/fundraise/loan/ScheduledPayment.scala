package com.particeep.api.models.fundraise.loan

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import play.api.libs.json.Json

case class ScheduledPayment(
    id:                    String                = "",
    created_at:            Option[ZonedDateTime] = None,
    payment_date:          ZonedDateTime,
    issuer_id:             String,
    issuer_type:           String,
    recipient_id:          String,
    recipient_type:        String,
    parent_id:             Option[String]        = None,
    parent_type:           Option[String]        = None,
    amount:                Int,
    fees:                  Int,
    status:                String,
    attempt_to_pay:        Int,
    max_attempt_to_pay:    Int,
    transaction_wallet_id: Option[String]        = None,
    tag:                   Option[String]        = None
)

object ScheduledPayment {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[ScheduledPayment]
}
