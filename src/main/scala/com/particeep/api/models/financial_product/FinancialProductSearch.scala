package com.particeep.api.models.financial_product

case class FinancialProductSearch(
  name:             Option[String],
  description:      Option[String],
  code_isin:        Option[String],
  risk_level:       Option[Int],
  enable_for_sales: Option[Boolean]
)
