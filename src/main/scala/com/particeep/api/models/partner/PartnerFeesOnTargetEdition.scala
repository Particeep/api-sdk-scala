package com.particeep.api.models.partner

import play.api.libs.json.{ JsObject, Json }

case class PartnerFeesOnTargetEdition(
  flat_fees:     Option[Int]      = None,
  variable_fees: Option[Double]   = None,
  tag:           Option[String]   = None,
  custom:        Option[JsObject] = None
)

object PartnerFeesOnTargetEdition {
  val format = Json.format[PartnerFeesOnTargetEdition]
}
