package com.particeep.api.models.fundraise.loan

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import com.particeep.api.models.enums.CalculatorType.CalculatorType
import play.api.libs.json.Json

case class LoanOffer(
  term:                 Int                    = 0,
  rate:                 Double                 = 0,
  tax_rate:             Double                 = 0,
  amount_min:           Int                    = 0,
  method:               Option[CalculatorType] = None,
  repayment_frequency:  Option[Int]            = None,
  repayment_start_date: Option[ZonedDateTime]  = None
)

object LoanOffer {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[LoanOffer]
}
