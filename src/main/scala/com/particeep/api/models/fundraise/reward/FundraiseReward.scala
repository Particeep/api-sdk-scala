package com.particeep.api.models.fundraise.reward

import java.time.ZonedDateTime

import com.particeep.api.models.enums.Currency.{Currency, EUR}
import com.particeep.api.models.enums.FundraiseStatus.{FundraiseStatus, INIT}
import play.api.libs.json.Json

/**
 * Created by Noe on 26/01/2017.
 */
case class FundraiseReward(
  id:             Option[String]        = None,
  enterprise_id:  Option[String]        = None,
  recipient_id:   Option[String]        = None,
  recipient_type: Option[String]        = None,
  name:           String                = "",
  start_at:       Option[ZonedDateTime] = None,
  end_at:         Option[ZonedDateTime] = None,
  amount_target:  Int                   = 0,
  currency:       Currency              = EUR,
  status:         FundraiseStatus       = INIT,
  tags:           Option[String]        = None
)

object FundraiseReward {
  val format = Json.format[FundraiseReward]
}
