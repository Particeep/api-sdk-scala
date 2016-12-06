package com.particeep.api

import java.time.ZonedDateTime

import com.particeep.api.core.{ResponseParser, WSClient}
import play.api.libs.json._

import scala.concurrent.{ExecutionContext, Future}
import com.particeep.api.models._
import com.particeep.api.models.enums.Gender.Gender

case class User(
  id:                       String                = "",
  email:                    Option[String]        = None,
  password:                 Option[String]        = None,
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
  has_been_claimed:         Option[Boolean]       = None,
  address:                  Option[Address]       = None
)

trait UserClient extends ResponseParser[User] {
  self: WSClient =>

  private val endPoint: String = "/user"
  implicit val format = User.format

  def byId(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, User]] = {
    url(s"$endPoint/$id", timeout).get().map(parse)
  }
}

object User {
  implicit val format = Json.format[User]
}
