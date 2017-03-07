package com.particeep.api.models.fundraise.loan

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import play.api.libs.json.Json

case class RepaymentVector(
  capital:                Double,
  interest:               Double,
  taxes:                  Double,
  amount:                 Double,
  capital_remains_to_pay: Double
)

case class RepaymentVectorWithDate(
  date:      ZonedDateTime,
  repayment: RepaymentVector
)

case class RepaymentInfoVector(
  repaymentInfo: RepaymentInfo,
  repayments:    List[RepaymentVectorWithDate]
)

object RepaymentInfoVector {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  private[this] implicit val repayment_info_format = RepaymentInfo.format
  private[this] implicit val repayment_vector_format = Json.format[RepaymentVector]
  private[this] implicit val repayment_vector_date_format = Json.format[RepaymentVectorWithDate]
  val format = Json.format[RepaymentInfoVector]
}
