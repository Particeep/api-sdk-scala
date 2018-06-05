package com.particeep.api.models.enterprise

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import com.particeep.api.models.Address
import play.api.libs.json.{ JsObject, Json }

case class Enterprise(
  id:                String                = "",
  created_at:        Option[ZonedDateTime] = None,
  creation_date:     Option[ZonedDateTime] = None,
  name:              String                = "",
  activity_domain:   Option[String]        = None,
  legal_status:      Option[String]        = None,
  description_short: Option[String]        = None,
  description_long:  Option[String]        = None,
  share_price:       Option[Int]           = None,
  url:               Option[String]        = None,
  logo_url:          Option[String]        = None,
  image_cover_url:   Option[String]        = None,
  video_url:         Option[String]        = None,
  website_url:       Option[String]        = None,
  status:            Option[String]        = None,
  tag:               Option[String]        = None,
  custom:            Option[JsObject]      = None,
  address:           Option[Address]       = None
)

object Enterprise {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[Enterprise]
}
