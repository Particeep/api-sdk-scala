package com.particeep.api.models.fundraise.loan

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import play.api.libs.json.Json

case class RepaymentVector(
  capital:                BigDecimal,
  interest:               BigDecimal,
  taxes:                  BigDecimal,
  capital_remains_to_pay: BigDecimal
)

case class RepaymentVectorWithDate(
  date:      ZonedDateTime,
  repayment: RepaymentVector
)

case class RepaymentInfoVector(
  repayments: Option[List[RepaymentVectorWithDate]] = None
)

object RepaymentInfoVector {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  private[this] implicit val repayment_vector_format = Json.format[RepaymentVector]
  private[this] implicit val repayment_vector_date_format = Json.format[RepaymentVectorWithDate]
  val format = Json.format[RepaymentInfoVector]
}
