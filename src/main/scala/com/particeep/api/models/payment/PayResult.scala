package com.particeep.api.models.payment

import com.particeep.api.models.transaction.Transaction
import com.particeep.api.models.wallet.CashInBankAccount
import play.api.libs.json.Json

case class PayResult(
  transaction:  Transaction,
  payment_url:  Option[String]            = None,
  bank_account: Option[CashInBankAccount] = None
)

object PayResult {
  implicit val transaction_format = Transaction.format
  implicit val bank_account_format = CashInBankAccount.format
  val format = Json.format[PayResult]
}
