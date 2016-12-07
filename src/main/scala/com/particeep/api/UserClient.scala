package com.particeep.api

import java.time.ZonedDateTime

import com.particeep.api.core.{ResponseParser, WSClient}
import play.api.libs.json._

import scala.concurrent.{ExecutionContext, Future}
import com.particeep.api.models._
import com.particeep.api.models.enums.Gender.Gender
import com.particeep.api.utils.LangUtils

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

case class UserSearchCriteria(
  name:          Option[String] = None,
  gender:        Option[String] = None,
  birth_place:   Option[String] = None,
  nationality:   Option[String] = None,
  sector:        Option[String] = None,
  tags:          Option[String] = None,
  sort_by:       Option[String] = None,
  order_by:      Option[String] = Some("asc"),
  global_search: Option[String] = None,
  limit:         Option[Int]    = Some(30),
  offset:        Option[Int]    = Some(0)
)

object User {
  implicit val format = Json.format[User]
}

trait UserCapability {
  self: WSClient =>

  val user: UserClient = new UserClient(this)
}

class UserClient(ws: WSClient) extends ResponseParser {

  private val endPoint: String = "/user"
  implicit val format = User.format

  def byId(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, User]] = {
    ws.url(s"$endPoint/$id", timeout).get().map(parse[User])
  }

  def byIds(ids: Seq[String], timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[User]]] = {
    ws.url(s"$endPoint/$ids", timeout)
      .withQueryString("ids" -> ids.mkString(","))
      .get()
      .map(parse[List[User]])
  }

  def byEmail(email: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, User]] = {
    ws.url(s"$endPoint/email/$email", timeout).get().map(parse[User])
  }

  def searchByName(name: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[User]]] = {
    ws.url(s"$endPoint/name/$name", timeout).get.map(parse[List[User]])
  }

  def search(criteria: UserSearchCriteria, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginateSequence[User]]] = {
    ws.url(s"$endPoint/search", timeout)
      .withQueryString(LangUtils.productToQueryString(criteria): _*)
      .get
      .map(parse[PaginateSequence[User]])
  }

  def create(user: User, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, User]] = {
    ws.url(s"$endPoint", timeout).put(Json.toJson(user)).map(parse[User])
  }

  def update(id: String, user: User, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, User]] = {
    ws.url(s"$endPoint/$id", timeout).post(Json.toJson(user)).map(parse[User])
  }

  def authenticate(email: String, password: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, User]] = {
    val authenticateJson: JsValue = Json.toJson(Map("email" -> email, "password" -> password))
    ws.url(s"$endPoint/authenticate", timeout)
      .post(authenticateJson)
      .map(parse[User])
  }
}
