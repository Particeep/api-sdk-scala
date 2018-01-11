package com.particeep.api.models.role

case class RoleSearch(
  user_id:     Option[String] = None,
  role_name:   Option[String] = None,
  target_id:   Option[String] = None,
  target_type: Option[String] = None,
  tag:         Option[String] = None
)
