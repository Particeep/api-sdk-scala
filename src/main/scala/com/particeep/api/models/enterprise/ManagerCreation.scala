package com.particeep.api.models.enterprise

import play.api.libs.json.Json

/**
 * Created by Noe on 10/03/2017.
 */
case class ManagerCreation(
  new_manager_id: String,
  name:           Option[String],
  tag:            Option[String]
)

object ManagerCreation {
  val format = Json.format[ManagerCreation]
}
