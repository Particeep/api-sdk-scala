package com.particeep.api.models.enterprise

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import com.particeep.api.models.Address
import play.api.libs.json.{ JsObject, Json }

case class EnterpriseCreation(
  name:              String,
  user_creator_id:   String,
  creation_date:     Option[ZonedDateTime] = None,
  activity_domain:   Option[String]        = None,
  legal_status:      Option[String]        = None,
  description_short: Option[String]        = None,
  description_long:  Option[String]        = None,
  url:               Option[String]        = None,
  logo_url:          Option[String]        = None,
  image_cover_url:   Option[String]        = None,
  video_url:         Option[String]        = None,
  status:            Option[String]        = None,
  tag:               Option[String]        = None,
  custom:            Option[JsObject]      = None,
  address:           Option[Address]       = None
)

object EnterpriseCreation {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[EnterpriseCreation]
}
