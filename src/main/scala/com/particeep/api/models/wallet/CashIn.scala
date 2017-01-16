package com.particeep.api.models.wallet

import com.particeep.api.models.enums.Locale.Locale
import play.api.libs.json.Json

case class CashIn(
  amount:      Int            = 0,
  fees:        Option[Int]    = None,
  tag:         Option[String] = None,
  accept_url:  String         = "",
  decline_url: String         = "",
  pending_url: String         = "",
  owner_ip:    String         = "",
  locale:      Option[Locale] = None
)

object CashIn {
  val format = Json.format[CashIn]
}
