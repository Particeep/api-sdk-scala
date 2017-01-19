package com.particeep.api.models.role

import play.api.libs.json.Json

/**
 * Created by Noe on 19/01/2017.
 */

case class RoleCreation(
  target_id:   Option[String] = None,
  target_type: Option[String] = None
)

object RoleCreation {
  val format = Json.format[RoleCreation]
}
