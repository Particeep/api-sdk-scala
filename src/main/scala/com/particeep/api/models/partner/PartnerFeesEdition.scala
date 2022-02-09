package com.particeep.api.models.partner

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import play.api.libs.json.{ JsObject, Json }

case class PartnerFeesEdition(
    flat_fees:             Option[Int]           = None,
    variable_fees:         Option[Double]        = None,
    running_flat_fees:     Option[Int]           = None,
    running_variable_fees: Option[Double]        = None,
    frequency:             Option[Int]           = None,
    start_at:              Option[ZonedDateTime] = None,
    duration:              Option[Int]           = None,
    tag:                   Option[String]        = None,
    custom:                Option[JsObject]      = None
)

object PartnerFeesEdition {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[PartnerFeesEdition]
}
