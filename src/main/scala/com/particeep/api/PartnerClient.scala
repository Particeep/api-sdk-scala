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
  private implicit val partner_company_format = PartnerCompany.format
  private implicit val partner_company_creation_format = PartnerCompanyCreation.format
  private implicit val partner_company_edition_format = PartnerCompanyEdition.format
}

class PartnerClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends WithWS with WithCredentials with EntityClient {

  import PartnerClient._

  def createDefaultPartnerFees(
    user_id:               String,
    partner_fees_creation: PartnerFeesCreation,
    timeout:               Long                = defaultTimeOut
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, PartnerFees]] = {
    ws.put[PartnerFees](s"$endPoint/fees/$user_id/default", timeout, Json.toJson(partner_fees_creation))
  }

  def getDefaultPartnerFeesByUserId(user_id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PartnerFees]] = {
    ws.get[PartnerFees](s"$endPoint/fees/$user_id/default", timeout)
  }

  def updateDefaultPartnerFees(user_id: String, partner_fees_edition: PartnerFeesEdition, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PartnerFees]] = {
    ws.post[PartnerFees](s"$endPoint/fees/$user_id/default", timeout, Json.toJson(partner_fees_edition))
  }

  def deleteDefaultPartnerFees(user_id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PartnerFees]] = {
    ws.delete[PartnerFees](s"$endPoint/fees/$user_id/default", timeout)
  }

  def createPartnerFees(
    user_id:                         String,
    target_id:                       String,
    target_type:                     String,
    partner_fees_on_target_creation: PartnerFeesOnTargetCreation,
    timeout:                         Long                        = defaultTimeOut
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, PartnerFeesOnTarget]] = {
    ws.put[PartnerFeesOnTarget](
      s"$endPoint/fees/$user_id/$target_id/$target_type", timeout, Json.toJson(partner_fees_on_target_creation)
    )
  }

  def getPartnerFees(user_id: String, target_id: String, target_type: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PartnerFeesOnTarget]] = {
    ws.get[PartnerFeesOnTarget](s"$endPoint/fees/$user_id/$target_id/$target_type", timeout)
  }

  def updatePartnerFees(
    user_id:                        String,
    target_id:                      String,
    target_type:                    String,
    partner_fees_on_target_edition: PartnerFeesOnTargetEdition,
    timeout:                        Long                       = defaultTimeOut
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, PartnerFeesOnTarget]] = {
    ws.post[PartnerFeesOnTarget](
      s"$endPoint/fees/$user_id/$target_id/$target_type", timeout, Json.toJson(partner_fees_on_target_edition)
    )
  }

  def deletePartnerFees(user_id: String, target_id: String, target_type: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PartnerFeesOnTarget]] = {
    ws.delete[PartnerFeesOnTarget](s"$endPoint/fees/$user_id/$target_id/$target_type", timeout)
  }

  def createPartnerCompany(
    user_id:                  String,
    partner_company_creation: PartnerCompanyCreation,
    timeout:                  Long                   = defaultTimeOut
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, PartnerCompany]] = {
    ws.put[PartnerCompany](s"$endPoint/company/$user_id", timeout, Json.toJson(partner_company_creation))
  }

  def getPartnerCompanyByUserId(user_id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PartnerCompany]] = {
    ws.get[PartnerCompany](s"$endPoint/company/$user_id", timeout)
  }

  def updatePartnerCompany(user_id: String, partner_company_edition: PartnerCompanyEdition, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PartnerCompany]] = {
    ws.post[PartnerCompany](s"$endPoint/company/$user_id", timeout, Json.toJson(partner_company_edition))
  }

  def deletePartnerCompany(user_id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PartnerCompany]] = {
    ws.delete[PartnerCompany](s"$endPoint/company/$user_id", timeout)
  }
}
