package com.particeep.api.models.fundraise.loan

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import com.particeep.api.models.enums.CalculatorType.CalculatorType
import com.particeep.api.models.enums.Currency.Currency
import play.api.libs.json.Json

case class FundraiseLoanCreation(
  name:                 String,
  enterprise_id:        Option[String]         = None,
  recipient_id:         Option[String]         = None,
  recipient_type:       Option[String]         = None,
  start_at:             Option[ZonedDateTime]  = None,
  end_at:               Option[ZonedDateTime]  = None,
  amount_target:        Int,
  term:                 Int,
  rate:                 Double,
  tax_rate:             Double,
  amount_min:           Int,
  currency:             Option[Currency]       = None,
  method:               Option[CalculatorType] = None,
  repayment_frequency:  Option[Int]            = None,
  repayment_start_date: Option[ZonedDateTime]  = None,
  tags:                 Option[String]         = None
)

object FundraiseLoanCreation {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[FundraiseLoanCreation]
}
