package com.particeep.api.models.fundraise

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import play.api.libs.json.Json

case class FundraiseSearch(
  start_at:                  Option[ZonedDateTime] = None,
  end_at:                    Option[ZonedDateTime] = None,
  enterprise_id:             Option[String]        = None,
  manager_id:                Option[String]        = None,
  manager_email:             Option[String]        = None,
  fundraise_id:              Option[String]        = None,
  fundraise_type:            Option[String]        = None,
  name:                      Option[String]        = None,
  category:                  Option[String]        = None,
  city:                      Option[String]        = None,
  statuses:                  Option[String]        = None,
  amount_target_min:         Option[Long]          = None,
  amount_target_max:         Option[Long]          = None,
  amount_engaged_min:        Option[Long]          = None,
  amount_engaged_max:        Option[Long]          = None,
  percentage_completion_min: Option[Int]           = None,
  percentage_completion_max: Option[Int]           = None,
  score:                     Option[String]        = None,
  private_group_id:          Option[String]        = None,
  targeting_roles:           Option[String]        = None,
  tag:                       Option[String]        = None
)

object FundraiseSearch {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[FundraiseSearch]
}
