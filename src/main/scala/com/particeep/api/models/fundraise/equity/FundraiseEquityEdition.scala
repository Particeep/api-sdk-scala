package com.particeep.api.models.fundraise.equity

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import com.particeep.api.models.enums.Currency.Currency
import play.api.libs.json.{ JsObject, Json }

case class FundraiseEquityEdition(
  recipient_id:        Option[String]        = None,
  recipient_type:      Option[String]        = None,
  name:                Option[String]        = None,
  description_short:   Option[String]        = None,
  description_long:    Option[String]        = None,
  description_offline: Option[String]        = None,
  disclaimer_risk:     Option[String]        = None,
  disclaimer_fees:     Option[String]        = None,
  disclaimer_payment:  Option[String]        = None,
  start_at:            Option[ZonedDateTime] = None,
  end_at:              Option[ZonedDateTime] = None,
  amount_target:       Option[Long]          = None,
  amount_target_max:   Option[Long]          = None,
  currency:            Option[Currency]      = None,
  score:               Option[String]        = None,
  tag:                 Option[String]        = None,
  custom:              Option[JsObject]      = None,
  restricted_to_group: Option[String]        = None,
  offer:               Option[EquityOffer]   = None
)

object FundraiseEquityEdition {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  implicit lazy val equity_offer_format = EquityOffer.format
  val format = Json.format[FundraiseEquityEdition]
}
