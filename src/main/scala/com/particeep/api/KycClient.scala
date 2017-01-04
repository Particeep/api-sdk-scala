package com.particeep.api

import com.particeep.api.core.{ResponseParser, WSClient}
import com.particeep.api.models.ErrorResult
import com.particeep.api.models.kyc.{KycCreation, KycGroup, KycsEdition}
import play.api.libs.json.Json
import play.api.mvc.Results

import scala.concurrent.{ExecutionContext, Future}

trait KycCapability {
  self: WSClient =>

  val kyc: KycClient = new KycClient(this)
}

class KycClient(ws: WSClient) extends ResponseParser {

  private val endPoint: String = "/kycs"
  implicit val group_format = KycGroup.format
  implicit val creation_format = KycCreation.format
  implicit val edition_format = KycsEdition.format

  def create(kyc_creation: KycCreation, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[KycGroup]]] = {
    ws.url(s"$endPoint", timeout).put(Json.toJson(kyc_creation)).map(parse[List[KycGroup]])
  }

  def update(kycs_edition: KycsEdition, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[KycGroup]]] = {
    ws.url(s"$endPoint", timeout).post(Json.toJson(kycs_edition)).map(parse[List[KycGroup]])
  }

  def askValidation(owner_id: String, owner_type: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[KycGroup]]] = {
    ws.url(s"$endPoint/askValidation/owner/$owner_id/$owner_type", timeout).post(Results.EmptyContent()).map(parse[List[KycGroup]])
  }

  def byOwnerIdAndType(owner_id: String, owner_type: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[KycGroup]]] = {
    ws.url(s"$endPoint/owner/$owner_id/$owner_type", timeout).get().map(parse[List[KycGroup]])
  }

  def cancel(owner_id: String, owner_type: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[KycGroup]]] = {
    ws.url(s"$endPoint/cancel/owner/$owner_id/$owner_type", timeout).delete().map(parse[List[KycGroup]])
  }
}
