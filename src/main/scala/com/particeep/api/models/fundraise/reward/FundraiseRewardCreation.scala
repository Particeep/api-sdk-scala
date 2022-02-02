package com.particeep.api.models.fundraise.reward

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import com.particeep.api.models.enums.Currency.Currency
import play.api.libs.json.{ JsObject, Json }

/**
 * Created by Noe on 26/01/2017.
 */
case class FundraiseRewardCreation(
    name:                String,
    description_short:   Option[String]        = None,
    description_long:    Option[String]        = None,
    description_offline: Option[String]        = None,
    enterprise_id:       Option[String],
    recipient_id:        Option[String],
    recipient_type:      Option[String],
    start_at:            Option[ZonedDateTime] = None,
    end_at:              Option[ZonedDateTime] = None,
    amount_target:       Long,
    currency:            Option[Currency]      = None,
    tag:                 Option[String]        = None,
    custom:              Option[JsObject]      = None
)

object FundraiseRewardCreation {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[FundraiseRewardCreation]
}
