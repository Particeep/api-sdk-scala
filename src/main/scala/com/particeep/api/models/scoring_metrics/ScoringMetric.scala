package com.particeep.api.models.scoring_metrics

import play.api.libs.json.{ JsObject, Json }
import java.time.ZonedDateTime

import com.particeep.api.core.Formatter

case class ScoringMetric(
  id:             String                = "",
  created_at:     Option[ZonedDateTime] = None,
  created_by:     Option[String]        = None,
  deleted_at:     Option[ZonedDateTime] = None,
  type_signature: TypeSignature,
  formula_name:   Option[String]        = None,
  formula_code:   String,
  code_checksum:  String,
  tag:            Option[String]        = None,
  custom:         Option[JsObject]      = None
)

object ScoringMetric {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[ScoringMetric]
}
