package com.particeep.api.models.fundraise.loan

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import play.api.libs.json.Json

case class FundraiseLoanSearch(
  start_date:     Option[ZonedDateTime] = None,
  end_date:       Option[ZonedDateTime] = None,
  status:         Option[String]        = None,
  enterprise_ids: Option[String]        = None,
  term_min:       Option[Int]           = None,
  term_max:       Option[Int]           = None,
  rate_min:       Option[Double]        = None,
  rate_max:       Option[Double]        = None
)

object FundraiseLoanSearch {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[FundraiseLoanSearch]
}
