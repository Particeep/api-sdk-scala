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

class KycClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends WithWS with WithCredentials with EntityClient {

  import KycClient._

  def create(kyc_creation: KycCreation, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[KycGroup]]] = {
    ws.put[List[KycGroup]](s"$endPoint", timeout, Json.toJson(kyc_creation))
  }

  def update(kycs_edition: KycsEdition, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[KycGroup]]] = {
    ws.post[List[KycGroup]](s"$endPoint", timeout, Json.toJson(kycs_edition))
  }

  def askValidation(
    owner_id:   String,
    owner_type: String,
    owner_ip:   Option[String] = None,
    timeout:    Long           = defaultTimeOut
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[KycGroup]]] = {
    owner_ip.map(ip => {
      ws.post[List[KycGroup]](s"$endPoint/askValidation/owner/$owner_id/$owner_type", timeout, Json.toJson(""), List("owner_ip" -> ip))
    }).getOrElse(
      ws.post[List[KycGroup]](s"$endPoint/askValidation/owner/$owner_id/$owner_type", timeout, Json.toJson(""))
    )
  }

  def byOwnerIdAndType(owner_id: String, owner_type: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[KycGroup]]] = {
    ws.get[List[KycGroup]](s"$endPoint/owner/$owner_id/$owner_type", timeout)
  }

  def cancel(
    owner_id:   String,
    owner_type: String,
    owner_ip:   Option[String] = None,
    timeout:    Long           = defaultTimeOut
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[KycGroup]]] = {
    owner_ip.map(ip => {
      ws.delete[List[KycGroup]](s"$endPoint/cancel/owner/$owner_id/$owner_type", timeout, Json.toJson(""), List("owner_ip" -> ip))
    }).getOrElse(
      ws.delete[List[KycGroup]](s"$endPoint/cancel/owner/$owner_id/$owner_type", timeout, Json.toJson(""))
    )
  }
}
