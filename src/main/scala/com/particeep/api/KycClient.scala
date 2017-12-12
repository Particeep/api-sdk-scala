package com.particeep.api

import com.particeep.api.core._
import com.particeep.api.models.ErrorResult
import com.particeep.api.models.kyc.{ KycCreation, KycGroup, KycsEdition }
import play.api.libs.json.Json

import scala.concurrent.{ ExecutionContext, Future }

trait KycCapability {
  self: WSClient =>

  val kyc: KycClient = new KycClient(this)
  def kyc(credentials: ApiCredential): KycClient = new KycClient(this, Some(credentials))
}

object KycClient {
  private val endPoint: String = "/kycs"
  private implicit val group_format = KycGroup.format
  private implicit val creation_format = KycCreation.format
  private implicit val edition_format = KycsEdition.format
}

class KycClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends ResponseParser with WithWS with WithCredentials with EntityClient {

  import KycClient._

  def create(kyc_creation: KycCreation, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[KycGroup]]] = {
    ws.url(s"$endPoint", timeout).put(Json.toJson(kyc_creation)).map(parse[List[KycGroup]])
  }

  def update(kycs_edition: KycsEdition, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[KycGroup]]] = {
    ws.url(s"$endPoint", timeout).post(Json.toJson(kycs_edition)).map(parse[List[KycGroup]])
  }

  def askValidation(
    owner_id:   String,
    owner_type: String,
    owner_ip:   Option[String] = None,
    timeout:    Long           = -1
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[KycGroup]]] = {
    val ws_url = ws.url(s"$endPoint/askValidation/owner/$owner_id/$owner_type", timeout)

    owner_ip.map(ip => {
      ws_url.addQueryStringParameters("owner_ip" -> ip).post(EmptyContent).map(parse[List[KycGroup]])
    }).getOrElse(
      ws_url.post(EmptyContent).map(parse[List[KycGroup]])
    )
  }

  def byOwnerIdAndType(owner_id: String, owner_type: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[KycGroup]]] = {
    ws.url(s"$endPoint/owner/$owner_id/$owner_type", timeout).get().map(parse[List[KycGroup]])
  }

  def cancel(
    owner_id:   String,
    owner_type: String,
    owner_ip:   Option[String] = None,
    timeout:    Long           = -1
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[KycGroup]]] = {
    val ws_url = ws.url(s"$endPoint/cancel/owner/$owner_id/$owner_type", timeout)

    owner_ip.map(ip => {
      ws_url.addQueryStringParameters("owner_ip" -> ip).delete().map(parse[List[KycGroup]])
    }).getOrElse(
      ws_url.delete().map(parse[List[KycGroup]])
    )
  }
}
