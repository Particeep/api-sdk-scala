package com.particeep.api

import com.particeep.api.core._
import com.particeep.api.models.ErrorResult
import com.particeep.api.models.partner._
import play.api.libs.json.Json

import scala.concurrent.{ ExecutionContext, Future }

trait PartnerCapability {
  self: WSClient =>

  val partner: PartnerClient = new PartnerClient(this)

  def partner(credentials: ApiCredential): PartnerClient = new PartnerClient(this, Some(credentials))
}

object PartnerClient {
  private val endPoint: String = "/partner"
  private implicit val partner_fees_format = PartnerFees.format
  private implicit val partner_fees_creation_format = PartnerFeesCreation.format
  private implicit val partner_fees_edition_format = PartnerFeesEdition.format
  private implicit val partner_fees_on_target_format = PartnerFeesOnTarget.format
  private implicit val partner_fees_on_target_creation_format = PartnerFeesOnTargetCreation.format
  private implicit val partner_fees_on_target_edition_format = PartnerFeesOnTargetEdition.format
}

class PartnerClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends ResponseParser with WithWS with WithCredentials with EntityClient {

  import PartnerClient._

  def createPartnerFees(partner_fees_creation: PartnerFeesCreation, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PartnerFees]] = {
    ws.url(s"$endPoint/fees", timeout).put(Json.toJson(partner_fees_creation)).map(parse[PartnerFees])
  }

  def getPartnerFeesByUserId(user_id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PartnerFees]] = {
    ws.url(s"$endPoint/fees/$user_id", timeout).get().map(parse[PartnerFees])
  }

  def updatePartnerFees(id: String, partner_fees_edition: PartnerFeesEdition, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PartnerFees]] = {
    ws.url(s"$endPoint/fees/$id", timeout).post(Json.toJson(partner_fees_edition)).map(parse[PartnerFees])
  }

  def deletePartnerFees(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PartnerFees]] = {
    ws.url(s"$endPoint/fees/$id", timeout).delete().map(parse[PartnerFees])
  }

  def createPartnerFeesOnTarget(partner_fees_on_target_creation: PartnerFeesOnTargetCreation, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PartnerFeesOnTarget]] = {
    ws.url(s"$endPoint/fees-on-target", timeout).put(Json.toJson(partner_fees_on_target_creation)).map(parse[PartnerFeesOnTarget])
  }

  def getPartnerFeesOnTargetByUserIdAndTarget(user_id: String, target_id: String, target_type: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PartnerFeesOnTarget]] = {
    ws.url(s"$endPoint/fees-on-target/$user_id/$target_id/$target_type", timeout).get().map(parse[PartnerFeesOnTarget])
  }

  def updatePartnerFeesOnTarget(id: String, partner_fees_on_target_edition: PartnerFeesOnTargetEdition, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PartnerFeesOnTarget]] = {
    ws.url(s"$endPoint/fees-on-target/$id", timeout).post(Json.toJson(partner_fees_on_target_edition)).map(parse[PartnerFeesOnTarget])
  }

  def deletePartnerFeesOnTarget(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PartnerFeesOnTarget]] = {
    ws.url(s"$endPoint/fees-on-target/$id", timeout).delete().map(parse[PartnerFeesOnTarget])
  }
}
