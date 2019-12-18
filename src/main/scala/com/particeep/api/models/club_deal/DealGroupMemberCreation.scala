package com.particeep.api.models.club_deal

import play.api.libs.json.Json

/**
 * Created by Noe on 04/07/2017.
 */
case class DealGroupMemberCreation(
    user_id: Option[String] = None,
    email:   String,
    tag:     Option[String] = None
)

object DealGroupMemberCreation {
  val format = Json.format[DealGroupMemberCreation]
}
