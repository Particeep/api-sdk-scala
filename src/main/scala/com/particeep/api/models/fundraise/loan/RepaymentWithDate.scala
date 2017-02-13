package com.particeep.api.models.fundraise.loan

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import play.api.libs.json.Json

case class Repayment(
  capital:  Int,
  interest: Int,
  taxes:    Int
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
