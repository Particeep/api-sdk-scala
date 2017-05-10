package com.particeep.api.models.fundraise.loan

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import com.particeep.api.models.enums.CalculatorType.CalculatorType
import play.api.libs.json.Json

case class RepaymentInfo(
  val method:               CalculatorType,
  val repayment_frequency:  Option[Int],
  val repayment_start_date: ZonedDateTime,
  val deferred_period:      Option[Int]     = None,
  val does_pay_taxes:       Option[Boolean] = None,
  val amount:               Option[Int]     = None
)

object RepaymentInfo {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[RepaymentInfo]
}
