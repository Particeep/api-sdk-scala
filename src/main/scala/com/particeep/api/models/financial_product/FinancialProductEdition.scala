package com.particeep.api.models.financial_product

import play.api.libs.json.{ JsObject, Json }

case class FinancialProductEdition(
  name:              Option[String]   = None,
  description:       Option[String]   = None,
  picture_url:       Option[String]   = None,
  price_per_share:   Option[Int]      = None,
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

object FinancialProductEdition {
  val format = Json.format[FinancialProductEdition]
}
