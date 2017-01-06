package com.particeep.api.models.role

import play.api.libs.json.Json

case class Roles(
  user_id: String       = "",
  roles:   List[String] = List()
)

object Roles {
  val format = Json.format[Roles]
}
