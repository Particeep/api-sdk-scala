package com.particeep.api.models.fundraise.loan

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import com.particeep.api.models.enums.CalculatorType.CalculatorType
import com.particeep.api.models.enums.Currency.{ Currency, EUR }
import com.particeep.api.models.enums.FundraiseStatus.{ FundraiseStatus, INIT }
import play.api.libs.json.Json

case class FundraiseLoan(
  id:                   String                 = "",
  created_at:           Option[ZonedDateTime]  = None,
  enterprise_id:        Option[String]         = None,
  recipient_id:         Option[String]         = None,
  recipient_type:       Option[String]         = None,
  name:                 String                 = "",
  start_at:             Option[String]         = None,
  end_at:               Option[String]         = None,
  amount_target:        Int                    = 0,
  currency:             Currency               = EUR,
  status:               FundraiseStatus        = INIT,
  tags:                 Option[String]         = None,
  term:                 Int                    = 0,
  rate:                 Double                 = 0,
  tax_rate:             Double                 = 0,
  amount_min:           Int                    = 0,
  method:               Option[CalculatorType],
  repayment_frequency:  Option[Int],
  repayment_start_date: Option[ZonedDateTime]
)

object FundraiseLoan {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[FundraiseLoan]
}
