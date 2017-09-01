package com.particeep.api.models.wallet.sepa

import com.particeep.api.models.enums.MandateLanguage.MandateLanguage
import play.api.libs.json.Json

case class SddMandateCreation(
  owner_ip:         String                  = "",
  holder:           String                  = "",
  bic:              String                  = "",
  iban:             String                  = "",
  is_b2b:           Option[Boolean]         = None,
  street:           String                  = "",
  zip:              String                  = "",
  city:             String                  = "",
  country:          String                  = "",
  mandate_language: Option[MandateLanguage] = None,
  tag:              Option[String]          = None
)

object SddMandateCreation {
  val format = Json.format[SddMandateCreation]
}
