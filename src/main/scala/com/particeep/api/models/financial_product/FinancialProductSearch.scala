package com.particeep.api.models.financial_product

case class FinancialProductSearch(
  name:             Option[String] = None,
  description:      Option[String] = None,
  code_isin:        Option[String] = None,
  risk_level:       Option[Int] = None,
  enable_for_sales: Option[Boolean] = None
)
