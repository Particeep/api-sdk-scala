package com.particeep.api.models.fundraise.loan

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import com.particeep.api.models.enums.Currency.Currency
import play.api.libs.json.Json

case class FundraiseLoanCreation(
  name:                String,
  description_short:   Option[String]        = None,
  description_long:    Option[String]        = None,
  description_offline: Option[String]        = None,
  enterprise_id:       Option[String]        = None,
  recipient_id:        Option[String]        = None,
  recipient_type:      Option[String]        = None,
  start_at:            Option[ZonedDateTime] = None,
  end_at:              Option[ZonedDateTime] = None,
  amount_target:       Int,
  currency:            Currency,
  score:               Option[String]        = None,
  tags:                Option[String]        = None,
  offer:               LoanOfferCreation
)

object FundraiseLoanCreation {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  implicit lazy val offer_loan_creation_format = LoanOfferCreation.format
  val format = Json.format[FundraiseLoanCreation]
}
