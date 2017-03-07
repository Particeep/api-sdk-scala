package com.particeep.api.models.fundraise.equity

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import play.api.libs.json.Json

case class FundraiseEquityEdition(
  recipient_id:        Option[String]        = None,
  recipient_type:      Option[String]        = None,
  name:                Option[String]        = None,
  start_at:            Option[ZonedDateTime] = None,
  end_at:              Option[ZonedDateTime] = None,
  amount_target:       Option[Int]           = None,
  amount_target_max:   Option[Int]           = None,
  tags:                Option[String]        = None,
  restricted_to_group: Option[String]        = None,
  offer:               Option[EquityOffer]   = None
)

object FundraiseEquityEdition {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  implicit lazy val equity_offer_format = EquityOffer.format
  val format = Json.format[FundraiseEquityEdition]
}