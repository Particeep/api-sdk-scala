package com.particeep.api.models.fundraise.loan

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import play.api.libs.json.{ JsObject, Json }

case class LoanRepaymentSchedule(
  id:                     String,
  created_at:             ZonedDateTime,
  user_id:                String,
  enterprise_id:          String,
  fundraise_id:           String,
  payment_date:           ZonedDateTime,
  capital:                Int,
  capital_remains_to_pay: Int,
  interest:               Int,
  tax:                    Int,
  fees:                   Int,
  transaction_id:         String,
  is_paid:                Boolean,
  is_offline:             Boolean,
  transaction_wallet_ids: Option[String]   = None,
  tag:                    Option[String]   = None,
  custom:                 Option[JsObject] = None
)

object LoanRepaymentSchedule {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[LoanRepaymentSchedule]
}
