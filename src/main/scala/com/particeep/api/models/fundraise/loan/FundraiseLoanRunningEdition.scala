package com.particeep.api.models.fundraise.loan

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import play.api.libs.json.Json

/**
 * Created by Noe on 16/05/2017.
 */
case class FundraiseLoanRunningEdition(
  name:                Option[String]        = None,
  description_short:   Option[String]        = None,
  description_long:    Option[String]        = None,
  description_offline: Option[String]        = None,
  end_at:              Option[ZonedDateTime] = None,
  score:               Option[String]        = None,
  tag:                 Option[String]        = None
)

object FundraiseLoanRunningEdition {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[FundraiseLoanRunningEdition]
}
