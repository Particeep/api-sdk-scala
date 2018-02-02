package com.particeep.api.models.financial_product

import play.api.libs.json.{ JsObject, Json }

case class FinancialProductCreation(
  name:   String,
  tag:    Option[String]   = None,
  custom: Option[JsObject] = None
)

object FinancialProductCreation {
  val format = Json.format[FinancialProductCreation]
}
