package com.particeep.api.models.payment

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import play.api.libs.json.Json

case class ScheduledPaymentCreation(
  payment_date:       ZonedDateTime,
  issuer_id:          String,
  issuer_type:        String,
  recipient_id:       String,
  recipient_type:     String,
  parent_id:          Option[String] = None,
  parent_type:        Option[String] = None,
  amount:             Int,
  fees:               Int,
  max_attempt_to_pay: Option[Int]    = None,
  tag:                Option[String] = None
)

object ScheduledPaymentCreation {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[ScheduledPaymentCreation]
}
