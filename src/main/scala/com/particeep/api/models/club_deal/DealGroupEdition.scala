package com.particeep.api.models.club_deal

import play.api.libs.json.Json

/**
 * Created by Noe on 04/07/2017.
 */
case class DealGroupEdition(
  name: Option[String] = None,
  tag:  Option[String] = None
)

object DealGroupEdition {
  val format = Json.format[DealGroupEdition]
}
