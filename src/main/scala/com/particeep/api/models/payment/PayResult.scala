package com.particeep.api.models.payment

import com.particeep.api.models.transaction.Transaction
import com.particeep.api.models.wallet.CashInBankAccount
import play.api.libs.json.Json

case class PayResult(
  transaction:  Option[Transaction],
  payment_url:  Option[String],
  bank_account: Option[CashInBankAccount]
)

object PayResult {
  implicit val transaction_format = Transaction.format
  implicit val bank_account_format = CashInBankAccount.format
  val format = Json.format[PayResult]
}
