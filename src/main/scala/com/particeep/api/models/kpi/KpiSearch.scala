package com.particeep.api.models.kpi

import java.time.ZonedDateTime

case class KpiSearch(
  name:          Option[String]        = None,
  user_id:       Option[String]        = None,
  target_id:     Option[String]        = None,
  target_type:   Option[String]        = None,
  tag:           Option[String]        = None,
  createdBefore: Option[ZonedDateTime] = None,
  createdAfter:  Option[ZonedDateTime] = None,
  date:          Option[ZonedDateTime] = None,
  value:         Option[Double]        = None,
  kpi_id:        Option[String]        = None,
  comment:       Option[String]        = None,
  valueTag:      Option[String]        = None
)
