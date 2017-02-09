package com.particeep.api.models.wallet

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import com.particeep.api.models.enums.Currency.Currency
import com.particeep.api.models.enums.WalletStatus.WalletStatus
import com.particeep.api.models.enums.WalletType.{ NATURAL, WalletType }
import play.api.libs.json.Json

case class Wallet(
  id:           String                = "",
  created_at:   Option[ZonedDateTime] = None,
  update_at:    Option[ZonedDateTime] = None,
  owner_id:     String                = "",
  owner_type:   String                = "",
  wallet_type:  WalletType            = NATURAL,
  status:       Option[WalletStatus]  = None,
  currency:     Option[Currency]      = None,
  company_name: Option[String]        = None,
  amount:       Int                   = 0
)

object Wallet {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[Wallet]
}

