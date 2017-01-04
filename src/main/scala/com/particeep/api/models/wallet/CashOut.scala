package com.particeep.api.models.wallet

import play.api.libs.json.Json

case class CashOut(
  bank_account_id: Option[String],
  amount:          Int,
  fees:            Option[Int],
  tag:             Option[String]
)

object CashOut {
  val format = Json.format[CashOut]
}
