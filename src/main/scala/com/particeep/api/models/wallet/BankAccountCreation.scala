package com.particeep.api.models.wallet

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
  owner_ip:    Option[String] = None,
  target_id:   Option[String] = None
)

object BankAccountCreation {
  val format = Json.format[BankAccountCreation]
}
