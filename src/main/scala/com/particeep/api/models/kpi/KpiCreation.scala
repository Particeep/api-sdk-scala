package com.particeep.api.models.kpi

import play.api.libs.json.{ Json, JsObject }

case class KpiCreation(
  name:        String,
  user_id:     String,
  target_id:   Option[String],
  target_type: Option[String],
  tag:         Option[String],
  custom:      Option[JsObject],
  values:      Option[Seq[KpiValue]]
)

object KpiCreation {
  val format = Json.format[KpiCreation]
}
