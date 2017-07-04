package com.particeep.api.models.club_deal

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import play.api.libs.json.Json

/**
 * Created by Noe on 04/07/2017.
 */
case class DealGroupMember(
  id:            String                = "",
  created_at:    Option[ZonedDateTime] = None,
  deal_group_id: String                = "",
  user_id:       Option[String]        = None,
  email:         String                = "",
  tag:           Option[String]        = None
)

object DealGroupMember {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[DealGroupMember]
}
