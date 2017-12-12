package com.particeep.api

import com.particeep.api.core._
import com.particeep.api.models.{ ErrorResult, PaginatedSequence }
import com.particeep.api.models.role.{ RoleCreation, RoleSearch, Roles }
import com.particeep.api.utils.LangUtils
import play.api.libs.json.Json

import scala.concurrent.{ ExecutionContext, Future }

trait RoleCapability {
  self: WSClient =>

  val role: RoleClient = new RoleClient(this)
  def role(credentials: ApiCredential): RoleClient = new RoleClient(this, Some(credentials))
}

object RoleClient {
  private val endPoint: String = "/role"
  private implicit val format = Roles.format
  private implicit val creation_format = RoleCreation.format
}

class RoleClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends ResponseParser with WithWS with WithCredentials with EntityClient {

  import RoleClient._

  def all(timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[String]]] = {
    ws.url(s"$endPoint/all", timeout).get().map(parse[List[String]])
  }

  def allByUser(user_id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Roles]] = {
    ws.url(s"$endPoint/all_by_user/$user_id", timeout).get().map(parse[Roles])
  }

  def add(user_id: String, role: String, role_creation: RoleCreation, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Roles]] = {
    ws.url(s"$endPoint/$user_id/add/${role.toLowerCase}", timeout).put(Json.toJson(role_creation)).map(parse[Roles])
  }

  private[this] case class TargetInfo(target_id: Option[String], target_type: Option[String])
  def remove(
    user_id:     String,
    role:        String,
    target_id:   Option[String] = None,
    target_type: Option[String] = None,
    timeout:     Long           = -1
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, Roles]] = {
    ws.url(s"$endPoint/$user_id/remove/${role.toLowerCase}", timeout)
      .addQueryStringParameters(LangUtils.productToQueryString(TargetInfo(target_id, target_type)): _*)
      .delete()
      .map(parse[Roles])
  }

  def hasRole(user_id: String, role: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Boolean]] = {
    allByUser(user_id).map(result => result.right.map(roles => roles.roles.map(_.role_name).contains(role)))
  }

  def search(criteria: RoleSearch, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[Roles]]] = {
    ws.url(s"$endPoint/search", timeout)
      .addQueryStringParameters(LangUtils.productToQueryString(criteria): _*)
      .get
      .map(parse[PaginatedSequence[Roles]])
  }
}
