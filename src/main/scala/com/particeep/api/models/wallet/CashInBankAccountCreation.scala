package com.particeep.api.models.wallet

import play.api.libs.json.Json

case class CashInBankAccountCreation(
  amount: Int,
  fees:   Option[Int] = None
)

object CashInBankAccountCreation {
  val format = Json.format[CashInBankAccountCreation]
}
