package com.particeep.api.models.fundraise

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import com.particeep.api.models.enums.Currency.Currency
import org.cvogt.play.json.Jsonx
import play.api.libs.json.JsObject

case class FundraiseData(
  id:                String                = "",
  created_at:        Option[ZonedDateTime] = None,
  enterprise_id:     Option[String]        = None,
  manager_id:        Option[String]        = None,
  manager_email:     Option[String]        = None,
  fundraise_id:      Option[String]        = None,
  fundraise_type:    Option[String]        = None,
  currency:          Option[Currency]      = None,
  name:              Option[String]        = None,
  end_at:            Option[ZonedDateTime] = None,
  category:          Option[String]        = None,
  city:              Option[String]        = None,
  status:            Option[String]        = None,
  amount_target:     Option[Long]          = None,
  amount_engaged:    Option[Long]          = None,
  amount_target_max: Option[Long]          = None,
  transaction_count: Option[Int]           = None,
  tag:               Option[String]        = None,
  custom:            Option[JsObject]      = None,
  fundraise_tag:     Option[String]        = None,
  description_short: Option[String]        = None,
  logo_url:          Option[String]        = None,
  image_cover_url:   Option[String]        = None,
  score:             Option[String]        = None,
  visible:           Option[Boolean]       = None,
  private_group_id:  Option[String]        = None,
  targeting_roles:   Option[String]        = None
)

object FundraiseData {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Jsonx.formatCaseClass[FundraiseData]
}
