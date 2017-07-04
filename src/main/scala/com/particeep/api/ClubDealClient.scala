package com.particeep.api

import scala.concurrent.{ ExecutionContext, Future }
import com.particeep.api.models._
import com.particeep.api.models.club_deal._
import com.particeep.api.core._
import play.api.libs.json.Json
import play.api.mvc.Results

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
}

class ClubDealClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends ResponseParser with WithWS with WithCredentials with EntityClient {

  import ClubDealClient._

  def byId(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, DealGroup]] = {
    ws.url(s"$endPoint/$id", timeout).get().map(parse[DealGroup])
  }

  def create(deal_group_creation: DealGroupCreation, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, DealGroup]] = {
    ws.url(s"$endPoint", timeout).put(Json.toJson(deal_group_creation)).map(parse[DealGroup])
  }

  def update(id: String, deal_group_edition: DealGroupEdition, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, DealGroup]] = {
    ws.url(s"$endPoint/$id", timeout).post(Json.toJson(deal_group_edition)).map(parse[DealGroup])
  }

  def delete(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, DealGroup]] = {
    ws.url(s"$endPoint/$id", timeout).delete().map(parse[DealGroup])
  }

  def openDeal(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, DealGroup]] = {
    ws.url(s"$endPoint/$id/open", timeout).post(Results.EmptyContent()).map(parse[DealGroup])
  }

  def addMembers(id: String, deal_group_members: Seq[DealGroupMemberCreation], timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Seq[DealGroupMember]]] = {
    ws.url(s"$endPoint/$id/members", timeout).post(Json.toJson(deal_group_members)).map(parse[Seq[DealGroupMember]])
  }

  def removeMembers(id: String, emails: List[String], timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Seq[DealGroupMember]]] = {
    ws.url(s"$endPoint/$id/members", timeout).withMethod("DELETE").withBody(Json.toJson(emails)).execute().map(parse[Seq[DealGroupMember]])
  }

  def getMembers(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Seq[DealGroupMember]]] = {
    ws.url(s"$endPoint/$id/members", timeout).get().map(parse[Seq[DealGroupMember]])
  }

  def allDealWhereIsMember(email: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Seq[DealGroup]]] = {
    ws.url(s"$endPoint/all-by-user/$email", timeout).get().map(parse[Seq[DealGroup]])
  }

}
