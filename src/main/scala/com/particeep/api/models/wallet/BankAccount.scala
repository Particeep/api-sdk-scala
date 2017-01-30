package com.particeep.api.models.wallet

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import com.particeep.api.models.enums.BankAccountStatus.BankAccountStatus
import play.api.libs.json.Json

case class BankAccount(
  id:          String                    = "",
  created_at:  Option[ZonedDateTime]     = None,
  wallet_id:   Option[String]            = None,
  status:      Option[BankAccountStatus] = None,
  bank_name:   String                    = "",
  iban:        String                    = "",
  bic:         String                    = "",
  acct_num:    Option[String]            = None,
  aba_num:     Option[String]            = None,
  transit_num: Option[String]            = None
)

object BankAccount {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[BankAccount]
}
