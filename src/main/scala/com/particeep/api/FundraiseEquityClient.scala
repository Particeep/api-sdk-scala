package com.particeep.api

import com.particeep.api.core._
import com.particeep.api.models.enums.FundraiseStatus.FundraiseStatus
import com.particeep.api.models.{ ErrorResult, PaginatedSequence, TableSearch }
import com.particeep.api.models.fundraise.equity._
import com.particeep.api.models.imports.ImportResult
import com.particeep.api.models.transaction.{ Transaction, TransactionSearch }
import com.particeep.api.utils.LangUtils
import play.api.libs.Files.TemporaryFile
import play.api.libs.json.Json
import play.api.mvc.MultipartFormData

import scala.concurrent.{ ExecutionContext, Future }

trait FundraiseEquityCapability {
  self: WSClient =>

  val fundraise_equity: FundraiseEquityClient = new FundraiseEquityClient(this)

  def fundraise_equity(credentials: ApiCredential): FundraiseEquityClient = new FundraiseEquityClient(this, Some(credentials))
}

object FundraiseEquityClient {
  private val endPoint: String = "/equity"
  private val endPoint_import: String = "/import"
  private implicit val format = FundraiseEquity.format
  private implicit val creation_format = FundraiseEquityCreation.format
  private implicit val edition_format = FundraiseEquityEdition.format
  private implicit val running_edition_format = FundraiseEquityRunningEdition.format
  private implicit val investment_format = Investment.format
  private implicit val transaction_format = Transaction.format
  private implicit val investment_creation_format = InvestmentCreation.format
  private implicit val importResultReads = ImportResult.format[FundraiseEquity]
}

class FundraiseEquityClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends WithWS with WithCredentials with EntityClient {

  import FundraiseEquityClient._

  def byId(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseEquity]] = {
    ws.get[FundraiseEquity](s"$endPoint/fundraise/$id", timeout)
  }

  def byIds(ids: Seq[String], timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[FundraiseEquity]]] = {
    ws.get[List[FundraiseEquity]](s"$endPoint/fundraise", timeout, List("ids" -> ids.mkString(",")))
  }

  def create(fundraise_equity_creation: FundraiseEquityCreation, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseEquity]] = {
    ws.put[FundraiseEquity](s"$endPoint/fundraise", timeout, Json.toJson(fundraise_equity_creation))
  }

  def update(id: String, fundraise_equity_edition: FundraiseEquityEdition, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseEquity]] = {
    ws.post[FundraiseEquity](s"$endPoint/fundraise/$id", timeout, Json.toJson(fundraise_equity_edition))
  }

  def updateRunning(id: String, fundraise_equity_running_edition: FundraiseEquityRunningEdition, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseEquity]] = {
    ws.post[FundraiseEquity](s"$endPoint/fundraise/running/$id", timeout, Json.toJson(fundraise_equity_running_edition))
  }

  def search(criteria: FundraiseEquitySearch, table_criteria: TableSearch, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[FundraiseEquity]]] = {
    ws.get[PaginatedSequence[FundraiseEquity]](s"$endPoint/fundraises", timeout, LangUtils.productToQueryString(criteria) ++ LangUtils.productToQueryString(table_criteria))
  }

  def delete(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseEquity]] = {
    ws.delete[FundraiseEquity](s"$endPoint/fundraise/$id", timeout)
  }

  def updateStatus(id: String, new_status: FundraiseStatus, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseEquity]] = {
    ws.post[FundraiseEquity](s"$endPoint/fundraise/$id/status/$new_status", timeout, Json.toJson(""))
  }

  def allInvestmentsOnFundraise(
    id:             String,
    criteria:       TransactionSearch,
    table_criteria: TableSearch,
    timeout:        Long              = defaultTimeOut
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[Investment]]] = {
    ws.get[PaginatedSequence[Investment]](s"$endPoint/fundraise/$id/investments", timeout, LangUtils.productToQueryString(criteria) ++ LangUtils.productToQueryString(table_criteria))
  }

  def allInvestmentsByUser(
    user_id:        String,
    criteria:       TransactionSearch,
    table_criteria: TableSearch,
    timeout:        Long              = defaultTimeOut
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[Transaction]]] = {
    ws.get[PaginatedSequence[Transaction]](s"$endPoint/fundraise/investments/user/$user_id", timeout, LangUtils.productToQueryString(criteria) ++ LangUtils.productToQueryString(table_criteria))
  }

  def invest(id: String, investment_creation: InvestmentCreation, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Transaction]] = {
    ws.post[Transaction](s"$endPoint/fundraise/$id/invest", timeout, Json.toJson(investment_creation))
  }

  def importFromCsv(csv: MultipartFormData[TemporaryFile], timeout: Long = defaultImportTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, ImportResult[FundraiseEquity]]] = {
    ws.postFile[ImportResult[FundraiseEquity]](s"$endPoint_import/fundraise-equity/csv", timeout, csv, List())
  }
}
