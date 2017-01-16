package com.particeep.api.models.user

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import com.particeep.api.models.Address
import com.particeep.api.models.enums.Gender.Gender
import play.api.libs.json.Json

case class UserEdition(
  first_name:               Option[String]        = None,
  last_name:                Option[String]        = None,
  gender:                   Option[Gender]        = None,
  avatar_url:               Option[String]        = None,
  birthday:                 Option[ZonedDateTime] = None,
  birth_place:              Option[String]        = None,
  phone:                    Option[String]        = None,
  nationality:              Option[String]        = None,
  bio:                      Option[String]        = None,
  sector:                   Option[String]        = None,
  linkedin_url:             Option[String]        = None,
  viadeo_url:               Option[String]        = None,
  allow_mail_notifications: Option[Boolean]       = None,
  address:                  Option[Address]       = None
)

object UserEdition {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[UserEdition]
}
