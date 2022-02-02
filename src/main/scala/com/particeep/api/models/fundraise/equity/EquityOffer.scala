package com.particeep.api.models.fundraise.equity

import play.api.libs.json.Json

case class EquityOffer(
    financial_instrument: Option[String] = None,
    fees_in:              Option[Double] = None,
    fees_in_flat:         Option[Int]    = None,
    fees_out:             Option[Double] = None,
    tax_rate:             Option[Double] = None,
    price_per_share:      Option[Int]    = None,
    total_shares:         Option[Long]   = None,
    num_of_shares:        Option[Long]   = None,
    round:                Option[String] = None,
    valuation_pre_money:  Option[Long]   = None,
    min_commitment:       Option[Int]    = Some(1),
    max_commitment:       Option[Long]   = None
)

object EquityOffer {
  val format = Json.format[EquityOffer]
}
