package com.particeep.api.models.fundraise.loan

import play.api.libs.json.Json

case class RepaymentDetail(
  amount:          Int,
  user_first_name: Option[String],
  user_last_name:  Option[String],
  user_email:      String
)

object RepaymentDetail {
  val format = Json.format[RepaymentDetail]
}
