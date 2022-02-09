package com.particeep.api.models.scoring_metrics

import java.time.ZonedDateTime
case class ScoringMetricSearch(
    created_after:  Option[ZonedDateTime] = None,
    created_before: Option[ZonedDateTime] = None,
    formula_name:   Option[String]        = None,
    tag:            Option[String]        = None
)