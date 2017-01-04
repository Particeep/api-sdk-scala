package com.particeep.api.models

case class TableSearch(
  sort_by:       Option[String] = None,
  order_by:      Option[String] = Some("asc"),
  global_search: Option[String] = None,
  limit:         Option[Int]    = Some(30),
  offset:        Option[Int]    = Some(0)
)
