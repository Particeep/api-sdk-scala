package com.particeep.api.models.fundraise.equity

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import com.particeep.api.models.enums.Currency.{ Currency, EUR }
import com.particeep.api.models.enums.FundraiseStatus.{ FundraiseStatus, INIT }
import play.api.libs.json.Json

case class FundraiseEquity(
  id:                  String                = "",
  created_at:          Option[ZonedDateTime] = None,
  enterprise_id:       Option[String]        = None,
  recipient_id:        Option[String]        = None,
  recipient_type:      Option[String]        = None,
  name:                String                = "",
  start_at:            Option[ZonedDateTime] = None,
  end_at:              Option[ZonedDateTime] = None,
  amount_target:       Int                   = 0,
  amount_target_max:   Option[Int]           = None,
  currency:            Currency              = EUR,
  status:              FundraiseStatus       = INIT,
  tags:                Option[String]        = None,
  restricted_to_group: Option[String]        = None,
  offer:               EquityOffer           = EquityOffer()
)

object FundraiseEquity {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  implicit lazy val equity_offer_format = EquityOffer.format
  val format = Json.format[FundraiseEquity]
}