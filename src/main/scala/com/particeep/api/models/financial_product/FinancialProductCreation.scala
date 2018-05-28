package com.particeep.api.models.financial_product

import com.particeep.api.models.enums.Currency.Currency
import play.api.libs.json.{ JsObject, Json }

case class FinancialProductCreation(
  name:              String,
  description:       Option[String]   = None,
  picture_url:       Option[String]   = None,
  price_per_share:   Option[Int]      = None,
  currency:          Option[Currency] = None,
  num_of_shares:     Option[Long]     = None,
  num_of_shares_min: Option[Long]     = None,
  code_isin:         Option[String]   = None,
  risk_level:        Option[Int]      = None,
  doc_to_sign_url:   Option[String]   = None,
  external_link:     Option[String]   = None,
  enable_for_sales:  Option[Boolean]  = None,
  recipient_id:      Option[String]   = None,
  recipient_type:    Option[String]   = None,
  tag:               Option[String]   = None,
  custom:            Option[JsObject] = None
)

object FinancialProductCreation {
  val format = Json.format[FinancialProductCreation]
}
