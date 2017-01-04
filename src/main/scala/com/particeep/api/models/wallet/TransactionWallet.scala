package com.particeep.api.models.wallet

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import play.api.libs.json.Json

case class TransactionWallet(
  id:                 String                = "",
  created_at:         Option[ZonedDateTime] = None,
  debited_wallet_id:  Option[String]        = None,
  credited_wallet_id: Option[String]        = None,
  debited_amount:     Option[Int]           = None,
  credited_amount:    Option[Int]           = None,
  fees:               Option[Int]           = None,
  currency:           Option[String]        = None,
  status:             Option[String]        = None,
  tag:                Option[String]        = None
)

object TransactionWallet {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[TransactionWallet]
}
