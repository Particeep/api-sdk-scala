package com.particeep.api.models.financial_product

import play.api.libs.json.{ JsObject, Json }

case class FinancialProductEdition(
  name:   Option[String]   = None,
  tag:    Option[String]   = None,
  custom: Option[JsObject] = None
)

object FinancialProductEdition {
  val format = Json.format[FinancialProductEdition]
}
