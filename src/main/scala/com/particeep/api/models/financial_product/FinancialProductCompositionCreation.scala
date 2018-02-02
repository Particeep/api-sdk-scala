package com.particeep.api.models.financial_product

import play.api.libs.json.Json

case class FinancialProductCompositionCreation(
  inner_product_id: String,
  percentage:       Int
)

object FinancialProductCompositionCreation {
  val format = Json.format[FinancialProductCompositionCreation]
}
