package com.particeep.api.models.fundraise.reward

import java.time.ZonedDateTime

import play.api.libs.json.Json

/**
 * Created by Noe on 27/01/2017.
 */
case class FundraiseRewardSearch(
  start_date:     Option[ZonedDateTime] = None,
  end_date:       Option[ZonedDateTime] = None,
  status:         Option[String]        = None,
  enterprise_ids: Option[String]        = None,
  offset:         Option[Int]           = Some(0),
  limit:          Option[Int]           = Some(30)
)

object FundraiseRewardSearch {
  val format = Json.format[FundraiseRewardSearch]
}
