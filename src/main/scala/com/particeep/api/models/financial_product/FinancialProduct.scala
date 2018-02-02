package com.particeep.api.models.financial_product

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import play.api.libs.json.{ JsObject, Json }

case class FinancialProduct(
  id:                String                = "",
  created_at:        Option[ZonedDateTime] = None,
  last_updated_at:   Option[ZonedDateTime] = None,
  name:              String,
  description:       Option[String]        = None,
  picture_url:       Option[String]        = None,
  price_per_share:   Option[Int]           = None,
  num_of_shares:     Option[Long]          = None,
  num_of_shares_min: Option[Long]          = None,
  code_isin:         Option[String]        = None,
  risk_level:        Option[Int]           = None,
  doc_to_sign_url:   Option[String]        = None,
  external_link:     Option[String]        = None,
  enable_for_sales:  Boolean               = false,
  percentage:        Option[Int]           = None,
  tag:               Option[String]        = None,
  custom:            Option[JsObject]      = None
)

object FinancialProduct {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[FinancialProduct]
}
