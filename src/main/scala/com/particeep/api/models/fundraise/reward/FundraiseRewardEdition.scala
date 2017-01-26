package com.particeep.api.models.fundraise.reward

import java.time.ZonedDateTime

import com.particeep.api.models.enums.Currency.Currency
import play.api.libs.json.Json

/**
 * Created by Noe on 26/01/2017.
 */
case class FundraiseRewardEdition(
  name:           Option[String]        = None,
  recipient_id:   Option[String]        = None,
  recipient_type: Option[String]        = None,
  start_at:       Option[ZonedDateTime] = None,
  end_at:         Option[ZonedDateTime] = None,
  amount_target:  Option[Int]           = None,
  currency:       Option[Currency]      = None,
  tags:           Option[String]        = None
)

object FundraiseRewardEdition {
  val format = Json.format[FundraiseRewardEdition]
}
