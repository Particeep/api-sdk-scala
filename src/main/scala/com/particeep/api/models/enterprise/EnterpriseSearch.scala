package com.particeep.api.models.enterprise

import java.time.ZonedDateTime

/**
 * Created by Noe on 10/03/2017.
 */
case class EnterpriseSearch(
  name:              Option[String]        = None,
  activity_domains:  Option[String]        = None,
  legal_status:      Option[String]        = None,
  description_short: Option[String]        = None,
  description_long:  Option[String]        = None,
  city:              Option[String]        = None,
  ids:               Option[String]        = None,
  createdAfter:      Option[ZonedDateTime] = None,
  creationAfter:     Option[ZonedDateTime] = None,
  statuses:          Option[String]        = None,
  tag:               Option[String]        = None,
  sort_by:           Option[String]        = None,
  order_by:          Option[String]        = Some("asc"),
  global_search:     Option[String]        = None,
  limit:             Option[Int]           = Some(30),
  offset:            Option[Int]           = Some(0)
)
