package com.particeep.api.models.partner

import play.api.libs.json.{ JsObject, Json }

case class PartnerFeesOnTargetCreation(
  user_id:       String,
  target_id:     String,
  target_type:   String,
  flat_fees:     Option[Int]      = None,
  variable_fees: Option[Double]   = None,
  tag:           Option[String]   = None,
  custom:        Option[JsObject] = None
)

object PartnerFeesOnTargetCreation {
  val format = Json.format[PartnerFeesOnTargetCreation]
}
