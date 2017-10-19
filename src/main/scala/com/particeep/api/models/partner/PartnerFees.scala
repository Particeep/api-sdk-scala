package com.particeep.api.models.partner

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import play.api.libs.json.{ JsObject, Json }

case class PartnerFees(
  id:            String                = "",
  created_at:    Option[ZonedDateTime] = None,
  deleted_at:    Option[ZonedDateTime] = None,
  created_by:    Option[String]        = None,
  user_id:       String                = "",
  flat_fees:     Option[Int]           = None,
  variable_fees: Option[Double]        = None,
  tag:           Option[String]        = None,
  custom:        Option[JsObject]      = None
)

object PartnerFees {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[PartnerFees]
}