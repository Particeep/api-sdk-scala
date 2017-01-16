package com.particeep.api.models.wallet

import play.api.libs.json.Json

case class WalletTransfer(
  debited_wallet_id:  String         = "",
  credited_wallet_id: String         = "",
  amount:             Int            = 0,
  fees:               Option[Int]    = None,
  tag:                Option[String] = None
)

object WalletTransfer {
  val format = Json.format[WalletTransfer]
}
