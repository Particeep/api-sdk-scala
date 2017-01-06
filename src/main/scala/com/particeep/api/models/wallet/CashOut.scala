package com.particeep.api.models.wallet

import play.api.libs.json.Json

case class CashOut(
  bank_account_id: Option[String] = None,
  amount:          Int,
  fees:            Option[Int]    = None,
  tag:             Option[String] = None
)

object CashOut {
  val format = Json.format[CashOut]
}
