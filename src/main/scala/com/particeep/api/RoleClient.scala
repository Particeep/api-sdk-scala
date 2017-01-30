package com.particeep.api

import com.particeep.api.core.{ResponseParser, WSClient}
import com.particeep.api.models.ErrorResult
import com.particeep.api.models.role.{RoleCreation, Roles}
import play.api.libs.json.Json

import scala.concurrent.{ExecutionContext, Future}

trait RoleCapability {
  self: WSClient =>

  val role: RoleClient = new RoleClient(this)
}

class RoleClient(ws: WSClient) extends ResponseParser {

  private val endPoint: String = "/role"
  implicit val format = Roles.format
  implicit val creation_format = RoleCreation.format

  def all(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[String]]] = {
    ws.url(s"$endPoint/all", timeout).get().map(parse[List[String]])
  }

  def allByUser(user_id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Roles]] = {
    ws.url(s"$endPoint/all_by_user/$user_id", timeout).get().map(parse[Roles])
  }

  def add(user_id: String, role: String, role_creation: RoleCreation, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Roles]] = {
    ws.url(s"$endPoint/$user_id/add/${role.toLowerCase}", timeout).put(Json.toJson(role_creation)).map(parse[Roles])
  }

  def remove(user_id: String, role: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Roles]] = {
    ws.url(s"$endPoint/$user_id/remove/${role.toLowerCase}", timeout).delete().map(parse[Roles])
  }

  def hasRole(user_id: String, role: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Boolean]] = {
    allByUser(user_id).map(result => result.right.map(roles => roles.roles.map(_.role_name).contains(role)))
  }
}
