package com.particeep.api.models.fundraise

import java.time.ZonedDateTime

import ai.x.play.json.Jsonx
import com.particeep.api.core.Formatter

case class FundraiseSearch(
  created_before:            Option[ZonedDateTime] = None,
  created_after:             Option[ZonedDateTime] = None,
  end_before:                Option[ZonedDateTime] = None,
  end_after:                 Option[ZonedDateTime] = None,
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
  val format = Jsonx.formatCaseClass[FundraiseSearch]
}
