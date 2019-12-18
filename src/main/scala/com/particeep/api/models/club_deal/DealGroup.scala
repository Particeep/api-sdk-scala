package com.particeep.api.models.club_deal

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import play.api.libs.json.{ JsObject, Json }

/**
 * Created by Noe on 04/07/2017.
 */
case class DealGroup(
    id:          String                = "",
    created_at:  Option[ZonedDateTime] = None,
    name:        String                = "",
    target_id:   String                = "",
    target_type: String                = "",
    open:        Boolean               = false,
    tag:         Option[String]        = None,
    custom:      Option[JsObject]      = None
)

object DealGroup {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[DealGroup]
}
