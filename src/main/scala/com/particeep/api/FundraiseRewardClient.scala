package com.particeep.api

import java.io.File

import com.particeep.api.core._
import com.particeep.api.models.{ ErrorResult, PaginatedSequence, TableSearch }
import com.particeep.api.models.fundraise.reward._
import com.particeep.api.models.imports.ImportResult
import com.particeep.api.models.transaction.{ Transaction, TransactionSearch }
import com.particeep.api.utils.LangUtils
import play.api.libs.json.Json

import scala.concurrent.{ ExecutionContext, Future }

trait FundraiseRewardCapability {
  self: WSClient =>

  val fundraise_reward: FundraiseRewardClient = new FundraiseRewardClient(this)
  def fundraise_reward(credentials: ApiCredential): FundraiseRewardClient = new FundraiseRewardClient(this, Some(credentials))
}

object FundraiseRewardClient {
  private val endPoint: String = "/reward"
  private val endPoint_import: String = "/import"
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
  private implicit val importResultReads = ImportResult.format[FundraiseReward]
}

/**
 * Created by Noe on 26/01/2017.
 */
class FundraiseRewardClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends WithWS with WithCredentials with EntityClient {

  import FundraiseRewardClient._

  def byId(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseReward]] = {
    ws.get[FundraiseReward](s"$endPoint/fundraise/$id", timeout)
  }

  def byIds(ids: Seq[String], timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[FundraiseReward]]] = {
    ws.get[List[FundraiseReward]](s"$endPoint/fundraise", timeout, List("ids" -> ids.mkString(",")))
  }

  def search(
    criteria:       FundraiseRewardSearch,
    table_criteria: TableSearch,
    timeout:        Long                  = defaultTimeOut
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[FundraiseReward]]] = {
    ws.get[PaginatedSequence[FundraiseReward]](s"$endPoint/search", timeout, LangUtils.productToQueryString(criteria) ++ LangUtils.productToQueryString(table_criteria))
  }

  def create(fundraise_reward_creation: FundraiseRewardCreation, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseReward]] = {
    ws.put[FundraiseReward](s"$endPoint/fundraise", timeout, Json.toJson(fundraise_reward_creation))
  }

  def update(id: String, fundraise_reward_edition: FundraiseRewardEdition, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseReward]] = {
    ws.post[FundraiseReward](s"$endPoint/fundraise/$id", timeout, Json.toJson(fundraise_reward_edition))
  }

  def updateRunning(
    id:                               String,
    fundraise_reward_running_edition: FundraiseRewardRunningEdition,
    timeout:                          Long                          = defaultTimeOut
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseReward]] = {
    ws.post[FundraiseReward](s"$endPoint/fundraise/running/$id", timeout, Json.toJson(fundraise_reward_running_edition))
  }

  def delete(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseReward]] = {
    ws.delete[FundraiseReward](s"$endPoint/fundraise/$id", timeout)
  }

  def submitToReview(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseReward]] = {
    ws.post[FundraiseReward](s"$endPoint/fundraise/$id/submit", timeout, Json.toJson(""))
  }

  def reject(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseReward]] = {
    ws.post[FundraiseReward](s"$endPoint/fundraise/$id/reject", timeout, Json.toJson(""))
  }

  def launch(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseReward]] = {
    ws.post[FundraiseReward](s"$endPoint/fundraise/$id/launch", timeout, Json.toJson(""))
  }

  def close(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseReward]] = {
    ws.post[FundraiseReward](s"$endPoint/fundraise/$id/close", timeout, Json.toJson(""))
  }

  def refund(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseReward]] = {
    ws.post[FundraiseReward](s"$endPoint/fundraise/$id/refund", timeout, Json.toJson(""))
  }

  def addReward(fundraise_id: String, reward_creation: RewardCreation, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Reward]] = {
    ws.put[Reward](s"$endPoint/fundraise/$fundraise_id/reward", timeout, Json.toJson(reward_creation))
  }

  def updateReward(fundraise_id: String, id: String, reward_edition: RewardEdition, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Reward]] = {
    ws.post[Reward](s"$endPoint/fundraise/$fundraise_id/reward/$id", timeout, Json.toJson(reward_edition))
  }

  def deleteReward(fundraise_id: String, id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Reward]] = {
    ws.delete[Reward](s"$endPoint/fundraise/$fundraise_id/reward/$id", timeout)
  }

  def allRewardsByFundraise(fundraise_id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[Reward]]] = {
    ws.get[List[Reward]](s"$endPoint/fundraise/$fundraise_id/rewards", timeout)
  }

  def allBoughtRewardsByFundraise(
    fundraise_id:   String,
    criteria:       TransactionSearch,
    table_criteria: TableSearch,
    timeout:        Long              = defaultTimeOut
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[Backer]]] = {
    ws.get[PaginatedSequence[Backer]](
      s"$endPoint/fundraise/$fundraise_id/rewards/bought", timeout, LangUtils.productToQueryString(criteria) ++ LangUtils.productToQueryString(table_criteria)
    )
  }

  def quantityAllBoughtRewardsByFundraise(fundraise_id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Map[String, Int]]] = {
    ws.get[Map[String, Int]](s"$endPoint/fundraise/$fundraise_id/rewards/bought/quantity", timeout)
  }

  def allBoughtRewardsByUser(
    user_id:        String,
    criteria:       TransactionSearch,
    table_criteria: TableSearch,
    timeout:        Long              = defaultTimeOut
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[Backing]]] = {
    ws.get[PaginatedSequence[Backing]](
      s"$endPoint/$user_id/rewards", timeout, LangUtils.productToQueryString(criteria) ++ LangUtils.productToQueryString(table_criteria)
    )
  }

  def donate(fundraise_id: String, user_id: String, transaction_info: TransactionInfo, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Transaction]] = {
    ws.put[Transaction](s"$endPoint/fundraise/$fundraise_id/donate/$user_id", timeout, Json.toJson(transaction_info))
  }

  def buyReward(
    fundraise_id:     String,
    reward_id:        String,
    user_id:          String,
    transaction_info: TransactionInfo,
    timeout:          Long            = defaultTimeOut
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, Transaction]] = {
    ws.put[Transaction](s"$endPoint/fundraise/$fundraise_id/reward/$reward_id/buy/$user_id", timeout, Json.toJson(transaction_info))
  }

  def importFromCsv(csv: File, timeout: Long = defaultImportTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, ImportResult[FundraiseReward]]] = {
    ws.postFile[ImportResult[FundraiseReward]](s"$endPoint_import/fundraise-reward/csv", timeout, csv, "text/csv", List())
  }
}
