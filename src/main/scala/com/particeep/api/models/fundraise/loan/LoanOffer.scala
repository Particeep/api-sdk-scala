package com.particeep.api.models.fundraise.loan

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import com.particeep.api.models.enums.CalculatorType.CalculatorType
import play.api.libs.json.{ JsObject, Json }

case class LoanOffer(
    term:                 Int                    = 0,
    rate:                 Double                 = 0,
    tax_rate:             Double                 = 0,
    step:                 Int                    = 1,
    amount_min:           Option[Int]            = None,
    amount_max:           Option[Int]            = None,
    bond_price:           Option[Int]            = None,
    method:               Option[CalculatorType] = None,
    repayment_frequency:  Option[Int]            = None,
    repayment_start_date: Option[ZonedDateTime]  = None,
    deferred_period:      Option[Int]            = None,
    custom_schedule:      Option[JsObject]       = None
)

object LoanOffer {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[LoanOffer]
}
