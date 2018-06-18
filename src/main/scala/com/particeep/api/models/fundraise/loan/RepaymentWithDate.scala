package com.particeep.api.models.fundraise.loan

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import play.api.libs.json.Json

case class Repayment(
  capital:                        Int,
  capital_offline:                Option[Int] = None,
  interest:                       Int,
  interest_offline:               Option[Int] = None,
  taxes:                          Int,
  amount:                         Int,
  amount_offline:                 Option[Int] = None,
  capital_remains_to_pay:         Int,
  capital_remains_to_pay_offline: Option[Int] = None,
  fees:                           Int,
  fees_offline:                   Option[Int] = None,
  is_paid:                        Boolean,
  is_paid_offline:                Boolean
)

case class RepaymentWithDate(
  date:      ZonedDateTime,
  repayment: Repayment
)

object RepaymentWithDate {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  private[this] implicit val repayment_format = Json.format[Repayment]
  val format = Json.format[RepaymentWithDate]
}
