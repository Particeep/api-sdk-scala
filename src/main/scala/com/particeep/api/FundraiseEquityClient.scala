package com.particeep.api

import com.particeep.api.core.{ ApiCredential, ResponseParser, WSClient }
import com.particeep.api.models.enums.FundraiseStatus.FundraiseStatus
import com.particeep.api.models.{ ErrorResult, PaginatedSequence }
import com.particeep.api.models.fundraise.equity._
import com.particeep.api.models.transaction.{ Transaction, TransactionSearch }
import com.particeep.api.utils.LangUtils
import play.api.libs.json.Json
import play.api.mvc.Results

import scala.concurrent.{ ExecutionContext, Future }

class FundraiseEquityClient(ws: WSClient) extends ResponseParser {

  private[this] val endPoint: String = "/equity"
  implicit val format = FundraiseEquity.format
  implicit val creation_format = FundraiseEquityCreation.format
  implicit val edition_format = FundraiseEquityEdition.format
  implicit val investment_format = Investment.format
  implicit val transaction_format = Transaction.format
  implicit val investment_creation_format = InvestmentCreation.format

  def byId(id: String, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, FundraiseEquity]] = {
    ws.url(s"$endPoint/fundraise/$id", timeout).get().map(parse[FundraiseEquity])
  }

  def byIds(ids: Seq[String], timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, List[FundraiseEquity]]] = {
    ws.url(s"$endPoint/fundraise", timeout)
      .withQueryString("ids" -> ids.mkString(","))
      .get()
      .map(parse[List[FundraiseEquity]])
  }

  def create(fundraise_equity_creation: FundraiseEquityCreation, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, FundraiseEquity]] = {
    ws.url(s"$endPoint/fundraise", timeout).put(Json.toJson(fundraise_equity_creation)).map(parse[FundraiseEquity])
  }

  def update(id: String, fundraise_equity_edition: FundraiseEquityEdition, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, FundraiseEquity]] = {
    ws.url(s"$endPoint/fundraise/$id", timeout).post(Json.toJson(fundraise_equity_edition)).map(parse[FundraiseEquity])
  }

  def search(criteria: FundraiseEquitySearch, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, PaginatedSequence[FundraiseEquity]]] = {
    ws.url(s"$endPoint/fundraises", timeout)
      .withQueryString(LangUtils.productToQueryString(criteria): _*)
      .get
      .map(parse[PaginatedSequence[FundraiseEquity]])
  }

  def delete(id: String, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, FundraiseEquity]] = {
    ws.url(s"$endPoint/fundraise/$id", timeout).delete().map(parse[FundraiseEquity])
  }

  def updateStatus(id: String, new_status: FundraiseStatus, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, FundraiseEquity]] = {
    ws.url(s"$endPoint/fundraise/$id/status/$new_status", timeout).post(Results.EmptyContent()).map(parse[FundraiseEquity])
  }

  def allInvestmentsOnFundraise(id: String, criteria: TransactionSearch, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, PaginatedSequence[Investment]]] = {
    ws.url(s"$endPoint/fundraise/$id/investments", timeout)
      .withQueryString(LangUtils.productToQueryString(criteria): _*)
      .get
      .map(parse[PaginatedSequence[Investment]])
  }

  def allInvestmentsByUser(user_id: String, criteria: TransactionSearch, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, PaginatedSequence[Transaction]]] = {
    ws.url(s"$endPoint/fundraise/investments/user/$user_id", timeout)
      .withQueryString(LangUtils.productToQueryString(criteria): _*)
      .get
      .map(parse[PaginatedSequence[Transaction]])
  }

  def invest(id: String, investment_creation: InvestmentCreation, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, Transaction]] = {
    ws.url(s"$endPoint/fundraise/$id/invest", timeout).post(Json.toJson(investment_creation)).map(parse[Transaction])
  }
}
