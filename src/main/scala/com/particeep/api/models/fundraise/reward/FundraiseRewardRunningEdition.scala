package com.particeep.api.models.fundraise.reward

import java.time.ZonedDateTime

import play.api.libs.json.Json

/**
 * Created by Noe on 26/01/2017.
 */
case class FundraiseRewardRunningEdition(
  end_at: Option[ZonedDateTime] = None
)
object FundraiseRewardRunningEdition {
  val format = Json.format[FundraiseRewardRunningEdition]
}

