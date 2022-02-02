package com.particeep.api.models.kpi

import play.api.libs.json.{ Json, JsObject }

case class KpiUpdate(
    name:        String,
    user_id:     String,
    target_id:   Option[String]        = None,
    target_type: Option[String]        = None,
    tag:         Option[String]        = None,
    custom:      Option[JsObject]      = None,
    values:      Option[Seq[KpiValue]] = None
)

object KpiUpdate {
  val format = Json.format[KpiUpdate]
}
