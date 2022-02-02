package com.particeep.api.models.fundraise.loan

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import com.particeep.api.models.enums.CalculatorType.CalculatorType
import play.api.libs.json.{ JsObject, Json }

case class LoanOfferCreation(
    term:                 Int,
    rate:                 Double,
    tax_rate:             Option[Double]         = None,
    step:                 Option[Int]            = None,
    amount_min:           Option[Int]            = None,
    amount_max:           Option[Int]            = None,
    bond_price:           Option[Int]            = None,
    method:               Option[CalculatorType] = None,
    repayment_frequency:  Option[Int]            = None,
    repayment_start_date: Option[ZonedDateTime]  = None,
    deferred_period:      Option[Int]            = None,
    custom_schedule:      Option[JsObject]       = None
)

object LoanOfferCreation {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[LoanOfferCreation]
}
