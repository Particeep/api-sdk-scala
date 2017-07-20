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
  description_short:   Option[String]        = None,
  description_long:    Option[String]        = None,
  description_offline: Option[String]        = None,
  disclaimer_risk:     Option[String]        = None,
  disclaimer_fees:     Option[String]        = None,
  disclaimer_payment:  Option[String]        = None,
  start_at:            Option[String] = None,
  end_at:              Option[String] = None,
  amount_target:       Long                  = 0,
  amount_target_max:   Option[Long]          = None,
  currency:            Currency              = EUR,
  status:              FundraiseStatus       = INIT,
  score:               Option[String]        = None,
  tag:                 Option[String]        = None,
  private_group_id:    Option[String]        = None,
  offer:               EquityOffer           = EquityOffer()
)

object FundraiseEquity {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  implicit lazy val equity_offer_format = EquityOffer.format
  val format = Json.format[FundraiseEquity]
}
