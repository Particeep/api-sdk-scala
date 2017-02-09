package com.particeep.api

import com.particeep.api.core.{ ResponseParser, WSClient }
import com.particeep.api.models.ErrorResult
import com.particeep.api.models.enterprise.{ Enterprise, EnterpriseCreation, EnterpriseEdition, ManagerLink }
import play.api.libs.json.Json

import scala.concurrent.{ ExecutionContext, Future }

trait EnterpriseCapability {
  self: WSClient =>

  val enterprise: EnterpriseClient = new EnterpriseClient(this)
}

class EnterpriseClient(ws: WSClient) extends ResponseParser {

  private[this] val endPoint: String = "/enterprise"
  implicit val format = Enterprise.format
  implicit val creation_format = EnterpriseCreation.format
  implicit val edition_format = EnterpriseEdition.format
  implicit val manager_link_format = ManagerLink.format

  def byId(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Enterprise]] = {
    ws.url(s"$endPoint/$id", timeout).get().map(parse[Enterprise])
  }

  def byIds(ids: List[String], timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[Enterprise]]] = {
    ws.url(s"$endPoint", timeout)
      .withQueryString("ids" -> ids.mkString(","))
      .get()
      .map(parse[List[Enterprise]])
  }

  def byUserId(user_id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[Enterprise]]] = {
    ws.url(s"$endPoint/user/$user_id", timeout).get().map(parse[List[Enterprise]])
  }

  def create(enterprise_creation: EnterpriseCreation, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Enterprise]] = {
    ws.url(s"$endPoint", timeout).put(Json.toJson(enterprise_creation)).map(parse[Enterprise])
  }

  def update(id: String, enterprise_edition: EnterpriseEdition, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Enterprise]] = {
    ws.url(s"$endPoint/$id", timeout).post(Json.toJson(enterprise_edition)).map(parse[Enterprise])
  }

  def getManagers(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[ManagerLink]]] = {
    ws.url(s"$endPoint/$id/manager", timeout).get().map(parse[List[ManagerLink]])
  }
}
