package com.particeep.api

import com.particeep.api.core.{ResponseParser, WSClient}
import com.particeep.api.models.ErrorResult
import com.particeep.api.models.enterpise.{Enterprise, EnterpriseCreation, EnterpriseEdition}
import play.api.libs.json.Json

import scala.concurrent.{ExecutionContext, Future}

trait EnterpriseCapability {
  self: WSClient =>

  val enterprise: EnterpriseClient = new EnterpriseClient(this)
}

class EnterpriseClient(ws: WSClient) extends ResponseParser {

  private val endPoint: String = "/enterprise"
  implicit val format = Enterprise.format
  implicit val creation_format = EnterpriseCreation.format
  implicit val edition_format = EnterpriseEdition.format

  def byId(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Enterprise]] = {
    ws.url(s"$endPoint/$id", timeout).get().map(parse[Enterprise])
  }

  def byIds(ids: Seq[String], timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Seq[Enterprise]]] = {
    ws.url(s"$endPoint", timeout)
      .withQueryString("ids" -> ids.mkString(","))
      .get()
      .map(parse[Seq[Enterprise]])
  }

  def create(enterprise_creation: EnterpriseCreation, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Enterprise]] = {
    ws.url(s"$endPoint", timeout).put(Json.toJson(enterprise_creation)).map(parse[Enterprise])
  }

  def update(id: String, enterprise_edition: EnterpriseEdition, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Enterprise]] = {
    ws.url(s"$endPoint/$id", timeout).post(Json.toJson(enterprise_edition)).map(parse[Enterprise])
  }
}
