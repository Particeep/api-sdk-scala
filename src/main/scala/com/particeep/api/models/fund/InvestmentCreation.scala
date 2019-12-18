package com.particeep.api.models.fund

import play.api.libs.json.{ JsArray, JsObject, Json }

case class InvestmentCreation(
  user_id:            String,
  amount:             Int,
  check_required_pro: Option[Boolean]  = None,
  co_issuers:         Option[JsArray]  = None,
  custom:             Option[JsObject] = None
)

object InvestmentCreation {
  val format = Json.format[InvestmentCreation]
}
