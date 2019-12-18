package com.particeep.api.models.fundraise.reward

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import play.api.libs.json.{ JsObject, Json }

/**
 * Created by Noe on 26/01/2017.
 */
case class FundraiseRewardRunningEdition(
    name:                Option[String]        = None,
    description_short:   Option[String]        = None,
    description_long:    Option[String]        = None,
    description_offline: Option[String]        = None,
    end_at:              Option[ZonedDateTime] = None,
    tag:                 Option[String]        = None,
    custom:              Option[JsObject]      = None
)
object FundraiseRewardRunningEdition {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[FundraiseRewardRunningEdition]
}

