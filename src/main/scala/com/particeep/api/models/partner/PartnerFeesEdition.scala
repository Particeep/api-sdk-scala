package com.particeep.api.models.partner

import play.api.libs.json.{ JsObject, Json }

case class PartnerFeesEdition(
  flat_fees:     Option[Int]      = None,
  variable_fees: Option[Double]   = None,
  tag:           Option[String]   = None,
  custom:        Option[JsObject] = None
)

object PartnerFeesEdition {
  val format = Json.format[PartnerFeesEdition]
}
