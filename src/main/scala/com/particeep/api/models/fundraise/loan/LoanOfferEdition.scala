package com.particeep.api.models.fundraise.loan

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import com.particeep.api.models.enums.CalculatorType.CalculatorType
import play.api.libs.json.Json

case class LoanOfferEdition(
  term:                 Option[Int]            = None,
  rate:                 Option[Double]         = None,
  tax_rate:             Option[Double]         = None,
  step:                 Option[Int]            = None,
  amount_min:           Option[Int]            = None,
  amount_max:           Option[Int]            = None,
  bond_price:           Option[Int]            = None,
  method:               Option[CalculatorType] = None,
  repayment_frequency:  Option[Int]            = None,
  repayment_start_date: Option[ZonedDateTime]  = None,
  deferred_period:      Option[Int]            = None
)

object LoanOfferEdition {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[LoanOfferEdition]
}
