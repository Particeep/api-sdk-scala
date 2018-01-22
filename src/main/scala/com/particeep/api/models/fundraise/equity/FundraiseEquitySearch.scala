package com.particeep.api.models.fundraise.equity

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import play.api.libs.json.Json

case class FundraiseEquitySearch(
  start_date:     Option[ZonedDateTime] = None,
  end_date:       Option[ZonedDateTime] = None,
  status:         Option[String]        = None,
  enterprise_ids: Option[String]        = None
)

object FundraiseEquitySearch {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[FundraiseEquitySearch]
}
