package com.particeep.api.models.club_deal

import play.api.libs.json.Json

/**
 * Created by Noe on 04/07/2017.
 */
case class DealGroupCreation(
  name:        String,
  target_id:   String,
  target_type: String,
  tag:         Option[String] = None
)

object DealGroupCreation {
  val format = Json.format[DealGroupCreation]
}
