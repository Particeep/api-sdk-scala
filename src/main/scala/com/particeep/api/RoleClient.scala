package com.particeep.api

import com.particeep.api.core._
import com.particeep.api.models.{ ErrorResult, PaginatedSequence, TableSearch }
import com.particeep.api.models.role._
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
  private implicit val creations_format = RolesCreation.format
}

class RoleClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends WithWS with WithCredentials with EntityClient {

  import RoleClient._

  def all(timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[String]]] = {
    ws.get[List[String]](s"$endPoint/all", timeout)
  }

  def allByUser(user_id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Roles]] = {
    ws.get[Roles](s"$endPoint/all_by_user/$user_id", timeout)
  }

  def add(user_id: String, role: String, role_creation: RoleCreation, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Roles]] = {
    ws.put[Roles](s"$endPoint/$user_id/add/${role.toLowerCase}", timeout, Json.toJson(role_creation))
  }

  def addRoles(roles_creation: List[RolesCreation], timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[Roles]]] = {
    ws.put[List[Roles]](s"$endPoint", timeout, Json.toJson(roles_creation))
  }

  private[this] case class TargetInfo(target_id: Option[String], target_type: Option[String])
  def remove(
    user_id:     String,
    role:        String,
    target_id:   Option[String] = None,
    target_type: Option[String] = None,
    timeout:     Long           = defaultTimeOut
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, Roles]] = {
    ws.delete[Roles](
      s"$endPoint/$user_id/remove/${role.toLowerCase}",
      timeout,
      Json.toJson(""),
      LangUtils.productToQueryString(TargetInfo(target_id, target_type))
    )
  }

  def hasRole(user_id: String, role: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Boolean]] = {
    allByUser(user_id).map(result => result.right.map(roles => roles.roles.map(_.role_name).contains(role)))
  }

  def search(criteria: RoleSearch, table_criteria: TableSearch, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[Roles]]] = {
    ws.get[PaginatedSequence[Roles]](s"$endPoint/search", timeout, LangUtils.productToQueryString(criteria) ++ LangUtils.productToQueryString(table_criteria))
  }
}
