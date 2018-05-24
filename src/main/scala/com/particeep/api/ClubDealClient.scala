package com.particeep.api

import scala.concurrent.{ ExecutionContext, Future }
import com.particeep.api.models._
import com.particeep.api.models.club_deal._
import com.particeep.api.core._
import play.api.libs.json.Json

trait ClubDealCapability {
  self: WSClient =>

  val club_deal: ClubDealClient = new ClubDealClient(this)

  def club_deal(credentials: ApiCredential): ClubDealClient = new ClubDealClient(this, Some(credentials))
}

object ClubDealClient {
  private val endPoint: String = "/club-deal"
  private implicit val format = DealGroup.format
  private implicit val creation_format = DealGroupCreation.format
  private implicit val edition_format = DealGroupEdition.format
  private implicit val member_format = DealGroupMember.format
  private implicit val member_creation_format = DealGroupMemberCreation.format
  private implicit val email_list_format = EmailList.format
}

class ClubDealClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends WithWS with WithCredentials with EntityClient {

  import ClubDealClient._

  def byId(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, DealGroup]] = {
    ws.get[DealGroup](s"$endPoint/$id", timeout)
  }

  def create(deal_group_creation: DealGroupCreation, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, DealGroup]] = {
    ws.put[DealGroup](s"$endPoint", timeout, Json.toJson(deal_group_creation))
  }

  def update(id: String, deal_group_edition: DealGroupEdition, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, DealGroup]] = {
    ws.post[DealGroup](s"$endPoint/$id", timeout, Json.toJson(deal_group_edition))
  }

  def delete(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, DealGroup]] = {
    ws.delete[DealGroup](s"$endPoint/$id", timeout)
  }

  def openDeal(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, DealGroup]] = {
    ws.post[DealGroup](s"$endPoint/$id/open", timeout, Json.toJson(""))
  }

  def addMembers(id: String, deal_group_members: Seq[DealGroupMemberCreation], timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Seq[DealGroupMember]]] = {
    ws.post[Seq[DealGroupMember]](s"$endPoint/$id/members", timeout, Json.toJson(deal_group_members))
  }

  def removeMembers(id: String, emails: EmailList, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Seq[DealGroupMember]]] = {
    ws.delete[Seq[DealGroupMember]](s"$endPoint/$id/members", timeout, Json.toJson(emails))
  }

  def getMembers(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Seq[DealGroupMember]]] = {
    ws.get[Seq[DealGroupMember]](s"$endPoint/$id/members", timeout)
  }

  def allDealWhereIsMember(email: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Seq[DealGroup]]] = {
    ws.get[Seq[DealGroup]](s"$endPoint/all-by-user/$email", timeout)
  }

}
