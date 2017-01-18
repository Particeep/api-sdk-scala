package com.particeep.api.models.fundraise

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import com.particeep.api.models.enums.Currency.Currency
import play.api.libs.json.Json

case class FundraiseData(
  id:                String                = "",
  created_at:        Option[ZonedDateTime] = None,
  created_by:        Option[String]        = None,
  enterprise_id:     Option[String]        = None,
  fundraise_id:      Option[String]        = None,
  currency:          Option[Currency]      = None,
  name:              Option[String]        = None,
  end_at:            Option[ZonedDateTime] = None,
  category:          Option[String]        = None,
  city:              Option[String]        = None,
  status:            Option[String]        = None,
  amount_target:     Option[Int]           = None,
  amount_engaged:    Option[Int]           = None,
  transaction_count: Option[Int]           = None,
  tags:              Option[String]        = None,
  fundraise_tags:    Option[String]        = None,
  description_short: Option[String]        = None,
  logo_url:          Option[String]        = None,
  visible:           Option[Boolean]       = None,
  deleted_at:        Option[ZonedDateTime] = None
)

object FundraiseData {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[FundraiseData]
}
