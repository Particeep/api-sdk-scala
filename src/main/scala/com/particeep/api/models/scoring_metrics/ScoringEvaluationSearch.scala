package com.particeep.api.models.scoring_metrics

import com.particeep.api.core.Formatter
import org.joda.time.DateTime
import play.api.libs.json.Json

case class ScoringEvaluationSearch(
  created_after:  Option[DateTime] = None,
  created_before: Option[DateTime] = None,
  score:          Option[Long]     = None,
  metric_id:      Option[String]   = None,
  target_id:      Option[String]   = None,
  target_type:    Option[String]   = None,
  tag:            Option[String]   = None
)

object ScoringEvaluationSearch {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[ScoringEvaluationSearch]
}
