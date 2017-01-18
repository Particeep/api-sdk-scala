package com.particeep.api.models.fundraise.loan

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import com.particeep.api.models.enums.CalculatorType.CalculatorType
import com.particeep.api.models.enums.Currency.Currency
import play.api.libs.json.Json

case class FundraiseLoanEdition(
  name:                 Option[String]         = None,
  recipient_id:         Option[String]         = None,
  recipient_type:       Option[String]         = None,
  start_at:             Option[ZonedDateTime]  = None,
  end_at:               Option[ZonedDateTime]  = None,
  amount_target:        Option[Int]            = None,
  duration:             Option[Int]            = None,
  rate:                 Option[Double]         = None,
  tax_rate:             Option[Double]         = None,
  amount_min:           Option[Int]            = None,
  currency:             Option[Currency]       = None,
  method:               Option[CalculatorType] = None,
  repayment_frequency:  Option[Int]            = None,
  repayment_start_date: Option[ZonedDateTime]  = None,
  tags:                 Option[String]         = None
)

object FundraiseLoanEdition {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[FundraiseLoanEdition]
}
