package com.particeep.api.models.role

import java.time.ZonedDateTime

import play.api.libs.json.Json

/**
 * Created by Noe on 19/01/2017.
 */
case class Role(
  id:          String                = "",
  created_at:  Option[ZonedDateTime] = None,
  role_name:   String                = "",
  target_id:   Option[String]        = None,
  target_type: Option[String]        = None

)

object Role {
  val format = Json.format[Role]
}
