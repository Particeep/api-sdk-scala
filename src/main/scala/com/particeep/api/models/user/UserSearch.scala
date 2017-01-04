package com.particeep.api.models.user

import com.particeep.api.models.enums.Gender.Gender

case class UserSearch(
  name:          Option[String] = None,
  gender:        Option[Gender] = None,
  birth_place:   Option[String] = None,
  nationality:   Option[String] = None,
  sector:        Option[String] = None,
  tags:          Option[String] = None,
  sort_by:       Option[String] = None,
  order_by:      Option[String] = Some("asc"),
  global_search: Option[String] = None,
  limit:         Option[Int]    = Some(30),
  offset:        Option[Int]    = Some(0)
)
