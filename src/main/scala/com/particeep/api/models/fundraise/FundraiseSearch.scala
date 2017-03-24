package com.particeep.api.models.fundraise

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import play.api.libs.json.Json

case class FundraiseSearch(
  start_at:                  Option[ZonedDateTime] = None,
  end_at:                    Option[ZonedDateTime] = None,
  enterprise_id:             Option[String]        = None,
  manager_id:                Option[String]        = None,
  fundraise_type:            Option[String]        = None,
  name:                      Option[String]        = None,
  category:                  Option[String]        = None,
  city:                      Option[String]        = None,
  statuses:                  Option[String]        = None,
  amount_target_min:         Option[Int]           = None,
  amount_target_max:         Option[Int]           = None,
  amount_engaged_min:        Option[Int]           = None,
  amount_engaged_max:        Option[Int]           = None,
  percentage_completion_min: Option[Int]           = None,
  percentage_completion_max: Option[Int]           = None,
  score:                     Option[String]        = None,
  tags:                      Option[String]        = None,
  global_search:             Option[String]        = None,
  sort_by:                   Option[String]        = None,
  order_by:                  Option[String]        = Some("asc"),
  offset:                    Option[Int]           = Some(0),
  limit:                     Option[Int]           = Some(30)
)

object FundraiseSearch {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[FundraiseSearch]
}
