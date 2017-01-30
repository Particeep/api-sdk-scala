package com.particeep.api.models.wallet

import com.particeep.api.core.Formatter
import play.api.libs.json.Json

case class BankAccountCreation(
  bank_name:   String,
  iban:        String,
  bic:         String,
  number:      String,
  street:      String,
  zip:         String,
  city:        String,
  country:     String,
  acct_num:    Option[String] = None,
  aba_num:     Option[String] = None,
  transit_num: Option[String] = None,
  target_id:   Option[String] = None
)

object BankAccountCreation {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[BankAccountCreation]
}
