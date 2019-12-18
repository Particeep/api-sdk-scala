package com.particeep.api.models.fund

case class FundSearch(
  status:       Option[String]  = None,
  name:         Option[String]  = None,
  required_pro: Option[Boolean] = None
)
