package com.particeep.api.models.fundraise.loan

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import com.particeep.api.models.enums.Currency.Currency
import play.api.libs.json.Json

case class FundraiseLoanEdition(
  name:                Option[String]           = None,
  description_short:   Option[String]           = None,
  description_long:    Option[String]           = None,
  description_offline: Option[String]           = None,
  recipient_id:        Option[String]           = None,
  recipient_type:      Option[String]           = None,
  start_at:            Option[ZonedDateTime]    = None,
  end_at:              Option[ZonedDateTime]    = None,
  amount_target:       Option[Int]              = None,
  currency:            Option[Currency]         = None,
  score:               Option[String]           = None,
  tags:                Option[String]           = None,
  offer:               Option[LoanOfferEdition] = None
)

object FundraiseLoanEdition {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  implicit lazy val loan_offer_edition_format = LoanOfferEdition.format
  val format = Json.format[FundraiseLoanEdition]
}
