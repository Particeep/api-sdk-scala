package com.particeep.api

import com.particeep.api.core._
import com.particeep.api.models.enums.FundraiseStatus.FundraiseStatus
import com.particeep.api.models.{ ErrorResult, PaginatedSequence }
import com.particeep.api.models.fundraise.equity._
import com.particeep.api.models.transaction.{ Transaction, TransactionSearch }
import com.particeep.api.utils.LangUtils
import play.api.libs.json.Json
import play.api.mvc.Results

import scala.concurrent.{ ExecutionContext, Future }

trait FundraiseEquityCapability {
  self: WSClient =>

  val fundraise_equity: FundraiseEquityClient = new FundraiseEquityClient(this)

  def fundraise_equity(credentials: ApiCredential): FundraiseEquityClient = new FundraiseEquityClient(this, Some(credentials))
}

object FundraiseEquityClient {
  private val endPoint: String = "/equity"
  private implicit val format = FundraiseEquity.format
  private implicit val creation_format = FundraiseEquityCreation.format
  private implicit val edition_format = FundraiseEquityEdition.format
  private implicit val running_edition_format = FundraiseEquityRunningEdition.format
  private implicit val investment_format = Investment.format
  private implicit val transaction_format = Transaction.format
  private implicit val investment_creation_format = InvestmentCreation.format
}

class FundraiseEquityClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends ResponseParser with WithWS with WithCredentials with EntityClient {

  import FundraiseEquityClient._

  def byId(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseEquity]] = {
    ws.url(s"$endPoint/fundraise/$id", timeout).get().map(parse[FundraiseEquity])
  }

  def byIds(ids: Seq[String], timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[FundraiseEquity]]] = {
    ws.url(s"$endPoint/fundraise", timeout)
      .withQueryString("ids" -> ids.mkString(","))
      .get()
      .map(parse[List[FundraiseEquity]])
  }

  def create(fundraise_equity_creation: FundraiseEquityCreation, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseEquity]] = {
    ws.url(s"$endPoint/fundraise", timeout).put(Json.toJson(fundraise_equity_creation)).map(parse[FundraiseEquity])
  }

  def update(id: String, fundraise_equity_edition: FundraiseEquityEdition, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseEquity]] = {
    ws.url(s"$endPoint/fundraise/$id", timeout).post(Json.toJson(fundraise_equity_edition)).map(parse[FundraiseEquity])
  }

  def updateRunning(id: String, fundraise_equity_running_edition: FundraiseEquityRunningEdition, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseEquity]] = {
    ws.url(s"$endPoint/fundraise/running/$id", timeout).post(Json.toJson(fundraise_equity_running_edition)).map(parse[FundraiseEquity])
  }

  def search(criteria: FundraiseEquitySearch, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[FundraiseEquity]]] = {
    ws.url(s"$endPoint/fundraises", timeout)
      .withQueryString(LangUtils.productToQueryString(criteria): _*)
      .get
      .map(parse[PaginatedSequence[FundraiseEquity]])
  }

  def delete(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseEquity]] = {
    ws.url(s"$endPoint/fundraise/$id", timeout).delete().map(parse[FundraiseEquity])
  }

  def updateStatus(id: String, new_status: FundraiseStatus, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseEquity]] = {
    ws.url(s"$endPoint/fundraise/$id/status/$new_status", timeout).post(Results.EmptyContent()).map(parse[FundraiseEquity])
  }

  def allInvestmentsOnFundraise(id: String, criteria: TransactionSearch, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[Investment]]] = {
    ws.url(s"$endPoint/fundraise/$id/investments", timeout)
      .withQueryString(LangUtils.productToQueryString(criteria): _*)
      .get
      .map(parse[PaginatedSequence[Investment]])
  }

  def allInvestmentsByUser(user_id: String, criteria: TransactionSearch, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[Transaction]]] = {
    ws.url(s"$endPoint/fundraise/investments/user/$user_id", timeout)
      .withQueryString(LangUtils.productToQueryString(criteria): _*)
      .get
      .map(parse[PaginatedSequence[Transaction]])
  }

  def invest(id: String, investment_creation: InvestmentCreation, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Transaction]] = {
    ws.url(s"$endPoint/fundraise/$id/invest", timeout).post(Json.toJson(investment_creation)).map(parse[Transaction])
  }
}
