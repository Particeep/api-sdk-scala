package com.particeep.api.models.wallet

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import com.particeep.api.models.enums.Currency.Currency
import com.particeep.api.models.enums.TransactionWalletOperation.TransactionWalletOperation
import com.particeep.api.models.enums.TransactionWalletStatus.TransactionWalletStatus
import org.cvogt.play.json.Jsonx

case class TransactionWalletData(
  id:                 String,
  created_at:         Option[ZonedDateTime]              = None,
  debited_amount:     Option[Int]                        = None,
  credited_amount:    Option[Int]                        = None,
  fees:               Option[Int]                        = None,
  currency:           Option[Currency]                   = None,
  status:             Option[TransactionWalletStatus]    = None,
  tag:                Option[String]                     = None,
  debited_wallet_id:  Option[String]                     = None,
  credited_wallet_id: Option[String]                     = None,
  transaction_id:     Option[String]                     = None,
  operation:          Option[TransactionWalletOperation] = None,
  fundraise_id:       Option[String]                     = None,
  fundraise_name:     Option[String]                     = None
)

object TransactionWalletData {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Jsonx.formatCaseClass[TransactionWalletData]
}
