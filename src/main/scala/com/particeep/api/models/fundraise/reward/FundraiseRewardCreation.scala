package com.particeep.api.models.fundraise.reward

import java.time.ZonedDateTime

import com.particeep.api.models.enums.Currency.Currency
import play.api.libs.json.Json

/**
 * Created by Noe on 26/01/2017.
 */
case class FundraiseRewardCreation(
  name:           String,
  enterprise_id:  Option[String],
  recipient_id:   Option[String],
  recipient_type: Option[String],
  start_at:       Option[ZonedDateTime] = None,
  end_at:         Option[ZonedDateTime] = None,
  amount_target:  Int,
  currency:       Option[Currency]      = None,
  tags:           Option[String]        = None
)

object FundraiseRewardCreation {
  val format = Json.format[FundraiseRewardCreation]
}
