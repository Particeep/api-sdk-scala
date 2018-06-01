package com.particeep.api.models.fundraise.reward

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import com.particeep.api.models.enums.Currency.Currency
import play.api.libs.json.{ JsObject, Json }

/**
 * Created by Noe on 26/01/2017.
 */
case class FundraiseRewardEdition(
  name:                Option[String]        = None,
  description_short:   Option[String]        = None,
  description_long:    Option[String]        = None,
  description_offline: Option[String]        = None,
  recipient_id:        Option[String]        = None,
  recipient_type:      Option[String]        = None,
  start_at:            Option[ZonedDateTime] = None,
  end_at:              Option[ZonedDateTime] = None,
  amount_target:       Option[Long]          = None,
  currency:            Option[Currency]      = None,
  tag:                 Option[String]        = None,
  custom:              Option[JsObject]      = None
)

object FundraiseRewardEdition {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[FundraiseRewardEdition]
}
