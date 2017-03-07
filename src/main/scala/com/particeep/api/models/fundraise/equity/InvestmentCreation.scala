package com.particeep.api.models.fundraise.equity

import play.api.libs.json.Json

case class InvestmentCreation(
  user_id: String,
  amount:  Int
)

object InvestmentCreation {
  val format = Json.format[InvestmentCreation]
}
