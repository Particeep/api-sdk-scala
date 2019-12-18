package com.particeep.api.models.wallet

import play.api.libs.json.Json

case class CashInBankAccount(
    bank_name:      Option[String] = None,
    iban:           Option[String] = None,
    bic:            Option[String] = None,
    code_bank:      Option[String] = None,
    sort_code:      Option[String] = None,
    branch_code:    Option[String] = None,
    number_account: Option[String] = None,
    key_rib:        Option[String] = None,
    aba:            Option[String] = None,
    country:        Option[String] = None,
    label:          Option[String] = None,
    wire_reference: Option[String] = None,
    owner_name:     Option[String] = None,
    owner_address:  Option[String] = None
)

object CashInBankAccount {
  val format = Json.format[CashInBankAccount]
}
