package com.particeep.api

import com.particeep.api.core._
import com.particeep.api.models.{ ErrorResult, PaginatedSequence, TableSearch }
import com.particeep.api.models.fundraise.reward._
import com.particeep.api.models.transaction.{ Transaction, TransactionSearch }
import com.particeep.api.utils.LangUtils
import play.api.libs.json.Json
import play.api.mvc.Results

import scala.concurrent.{ ExecutionContext, Future }

trait FundraiseRewardCapability {
  self: WSClient =>

  val fundraise_reward: FundraiseRewardClient = new FundraiseRewardClient(this)
  def fundraise_reward(credentials: ApiCredential): FundraiseRewardClient = new FundraiseRewardClient(this, Some(credentials))
}

object FundraiseRewardClient {
  private val endPoint: String = "/reward"
  private implicit val format = FundraiseReward.format
  private implicit val creation_format = FundraiseRewardCreation.format
  private implicit val edition_format = FundraiseRewardEdition.format
  private implicit val edition_running_format = FundraiseRewardRunningEdition.format
  private implicit val reward_format = Reward.format
  private implicit val reward_creation_format = RewardCreation.format
  private implicit val reward_edition_format = RewardEdition.format
  private implicit val backer_format = Backer.format
  private implicit val backing_format = Backing.format
  private implicit val donation_format = TransactionInfo.format
  private implicit val transaction_format = Transaction.format
}

/**
 * Created by Noe on 26/01/2017.
 */
class FundraiseRewardClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends ResponseParser with WithWS with WithCredentials with EntityClient {

  import FundraiseRewardClient._

  def byId(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseReward]] = {
    ws.url(s"$endPoint/fundraise/$id", timeout).get().map(parse[FundraiseReward])
  }

  def byIds(ids: Seq[String], timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[FundraiseReward]]] = {
    ws.url(s"$endPoint/fundraise", timeout)
      .withQueryString("ids" -> ids.mkString(","))
      .get().map(parse[List[FundraiseReward]])
  }

  def search(criteria: FundraiseRewardSearch, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[FundraiseReward]]] = {
    ws.url(s"$endPoint/search", timeout)
      .withQueryString(LangUtils.productToQueryString(criteria): _*)
      .get().map(parse[PaginatedSequence[FundraiseReward]])
  }

  def create(fundraise_reward_creation: FundraiseRewardCreation, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseReward]] = {
    ws.url(s"$endPoint/fundraise", timeout).put(Json.toJson(fundraise_reward_creation)).map(parse[FundraiseReward])
  }

  def update(id: String, fundraise_reward_edition: FundraiseRewardEdition, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseReward]] = {
    ws.url(s"$endPoint/fundraise/$id", timeout).post(Json.toJson(fundraise_reward_edition)).map(parse[FundraiseReward])
  }

  def updateRunning(id: String, fundraise_reward_running_edition: FundraiseRewardRunningEdition, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseReward]] = {
    ws.url(s"$endPoint/fundraise/running/$id", timeout).post(Json.toJson(fundraise_reward_running_edition)).map(parse[FundraiseReward])
  }

  def delete(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseReward]] = {
    ws.url(s"$endPoint/fundraise/$id", timeout).delete().map(parse[FundraiseReward])
  }

  def submitToReview(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseReward]] = {
    ws.url(s"$endPoint/fundraise/$id/submit", timeout).post(Results.EmptyContent()).map(parse[FundraiseReward])
  }

  def reject(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseReward]] = {
    ws.url(s"$endPoint/fundraise/$id/reject", timeout).post(Results.EmptyContent()).map(parse[FundraiseReward])
  }

  def launch(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseReward]] = {
    ws.url(s"$endPoint/fundraise/$id/launch", timeout).post(Results.EmptyContent()).map(parse[FundraiseReward])
  }

  def close(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseReward]] = {
    ws.url(s"$endPoint/fundraise/$id/close", timeout).post(Results.EmptyContent()).map(parse[FundraiseReward])
  }

  def refund(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseReward]] = {
    ws.url(s"$endPoint/fundraise/$id/refund", timeout).post(Results.EmptyContent()).map(parse[FundraiseReward])
  }

  def addReward(fundraise_id: String, reward_creation: RewardCreation, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Reward]] = {
    ws.url(s"$endPoint/fundraise/$fundraise_id/reward", timeout).put(Json.toJson(reward_creation)).map(parse[Reward])
  }

  def updateReward(fundraise_id: String, id: String, reward_edition: RewardEdition, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Reward]] = {
    ws.url(s"$endPoint/fundraise/$fundraise_id/reward/$id", timeout).post(Json.toJson(reward_edition)).map(parse[Reward])
  }

  def deleteReward(fundraise_id: String, id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Reward]] = {
    ws.url(s"$endPoint/fundraise/$fundraise_id/reward/$id", timeout).delete().map(parse[Reward])
  }

  def allRewardsByFundraise(fundraise_id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[Reward]]] = {
    ws.url(s"$endPoint/fundraise/$fundraise_id/rewards", timeout).get().map(parse[List[Reward]])
  }

  def allBoughtRewardsByFundraise(fundraise_id: String, criteria: TransactionSearch, table_criteria: TableSearch, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[Backer]]] = {
    ws.url(s"$endPoint/fundraise/$fundraise_id/rewards/bought", timeout)
      .withQueryString(LangUtils.productToQueryString(criteria): _*)
      .withQueryString(LangUtils.productToQueryString(table_criteria): _*)
      .get().map(parse[PaginatedSequence[Backer]])
  }

  def quantityAllBoughtRewardsByFundraise(fundraise_id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Map[String, Int]]] = {
    ws.url(s"$endPoint/fundraise/$fundraise_id/rewards/bought/quantity", timeout).get().map(parse[Map[String, Int]])
  }

  def allBoughtRewardsByUser(user_id: String, criteria: TransactionSearch, table_criteria: TableSearch, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[Backing]]] = {
    ws.url(s"$endPoint/$user_id/rewards", timeout)
      .withQueryString(LangUtils.productToQueryString(criteria): _*)
      .withQueryString(LangUtils.productToQueryString(table_criteria): _*)
      .get().map(parse[PaginatedSequence[Backing]])
  }

  def donate(fundraise_id: String, user_id: String, transaction_info: TransactionInfo, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Transaction]] = {
    ws.url(s"$endPoint/fundraise/$fundraise_id/donate/$user_id", timeout).put(Json.toJson(transaction_info)).map(parse[Transaction])
  }

  def buyReward(fundraise_id: String, reward_id: String, user_id: String, transaction_info: TransactionInfo, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Transaction]] = {
    ws.url(s"$endPoint/fundraise/$fundraise_id/reward/$reward_id/buy/$user_id", timeout).put(Json.toJson(transaction_info)).map(parse[Transaction])
  }
}
