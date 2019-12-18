package com.particeep.api.models.fundraise.reward

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import com.particeep.api.models.enums.Currency.{ Currency, EUR }
import com.particeep.api.models.enums.FundraiseStatus.{ FundraiseStatus, INIT }
import play.api.libs.json.{ JsObject, Json }

/**
 * Created by Noe on 26/01/2017.
 */
case class FundraiseReward(
    id:                  Option[String]        = None,
    enterprise_id:       Option[String]        = None,
    recipient_id:        Option[String]        = None,
    recipient_type:      Option[String]        = None,
    name:                String                = "",
    description_short:   Option[String]        = None,
    description_long:    Option[String]        = None,
    description_offline: Option[String]        = None,
    start_at:            Option[ZonedDateTime] = None,
    end_at:              Option[ZonedDateTime] = None,
    amount_target:       Long                  = 0,
    currency:            Currency              = EUR,
    status:              FundraiseStatus       = INIT,
    tag:                 Option[String]        = None,
    custom:              Option[JsObject]      = None,
    private_group_id:    Option[String]        = None
)

object FundraiseReward {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[FundraiseReward]
}
