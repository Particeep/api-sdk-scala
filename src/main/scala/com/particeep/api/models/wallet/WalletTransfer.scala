package com.particeep.api.models.wallet

import play.api.libs.json.Json

case class WalletTransfer(
  debited_wallet_id:  String,
  credited_wallet_id: String,
  amount:             Int,
  fees:               Option[Int],
  tag:                Option[String]
)

object WalletTransfer {
  val format = Json.format[WalletTransfer]
}
