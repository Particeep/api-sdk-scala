package com.particeep.api.models.enterprise

import java.time.ZonedDateTime

/**
 * Created by Noe on 10/03/2017.
 */
case class EnterpriseSearch(
  name:              Option[String],
  activity_domains:  Option[String],
  legal_status:      Option[String],
  description_short: Option[String],
  description_long:  Option[String],
  city:              Option[String],
  ids:               Option[String],
  createdAfter:      Option[ZonedDateTime],
  creationAfter:     Option[ZonedDateTime],
  statuses:          Option[String],
  tag:               Option[String]
)
