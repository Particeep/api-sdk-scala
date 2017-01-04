package com.particeep.api.models.wallet

import com.particeep.api.models.enums.Locale.Locale
import play.api.libs.json.Json

case class CashIn(
  amount:      Int,
  fees:        Option[Int],
  tag:         Option[String],
  accept_url:  String,
  decline_url: String,
  pending_url: String,
  owner_ip:    String,
  locale:      Option[Locale]
)

object CashIn {
  val format = Json.format[CashIn]
}
