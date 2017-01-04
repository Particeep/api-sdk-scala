package com.particeep.api.models.user

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import com.particeep.api.models.Address
import com.particeep.api.models.enums.Gender.Gender
import play.api.libs.json.Json

case class UserEdition(
  first_name:               Option[String],
  last_name:                Option[String],
  gender:                   Option[Gender],
  avatar_url:               Option[String],
  birthday:                 Option[ZonedDateTime],
  birth_place:              Option[String],
  phone:                    Option[String],
  nationality:              Option[String],
  bio:                      Option[String],
  sector:                   Option[String],
  linkedin_url:             Option[String],
  viadeo_url:               Option[String],
  allow_mail_notifications: Option[Boolean],
  address:                  Option[Address]       = None
)

object UserEdition {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[UserEdition]
}
