package com.particeep.api

import com.particeep.api.core._
import com.particeep.api.models.{ ErrorResult, PaginatedSequence, TableSearch }
import com.particeep.api.models.fundraise.loan._
import com.particeep.api.models.imports.ImportResult
import com.particeep.api.models.transaction.{ Transaction, TransactionSearch }
import com.particeep.api.utils.LangUtils
import play.api.libs.Files.TemporaryFile
import play.api.libs.json.Json
import play.api.mvc.{ MultipartFormData, Results }

import scala.concurrent.{ ExecutionContext, Future }

trait FundraiseLoanCapability {
  self: WSClient =>

  val fundraise_loan: FundraiseLoanClient = new FundraiseLoanClient(this)
  def fundraise_loan(credentials: ApiCredential): FundraiseLoanClient = new FundraiseLoanClient(this, Some(credentials))
}

object FundraiseLoanClient {
  private val endPoint: String = "/loan"
  private val endPoint_import: String = "/import"
  private implicit val format = FundraiseLoan.format
  private implicit val creation_format = FundraiseLoanCreation.format
  private implicit val edition_format = FundraiseLoanEdition.format
  private implicit val running_edition_format = FundraiseLoanRunningEdition.format
  private implicit val repayment_info_format = RepaymentInfo.format
  private implicit val repayment_with_date_format = RepaymentWithDate.format
  private implicit val repayment_info_vector_format = RepaymentInfoVector.format
  private implicit val scheduled_payment_format = ScheduledPayment.format
  private implicit val lend_format = Lend.format
  private implicit val transaction_format = Transaction.format
  private implicit val lend_creation_format = LendCreation.format
  private implicit val estimate_borrower_info_format = EstimateBorrowerInfo.format
  private implicit val importResultReads = ImportResult.reads[FundraiseLoan]
}

class FundraiseLoanClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends ResponseParser with WithWS with WithCredentials with EntityClient {
  implicit val repayment_detail_format = RepaymentDetail.format

  import FundraiseLoanClient._

  def byId(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseLoan]] = {
    ws.url(s"$endPoint/fundraise/$id", timeout).get().map(parse[FundraiseLoan])
  }

  def byIds(ids: Seq[String], timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[FundraiseLoan]]] = {
    ws.url(s"$endPoint/fundraise", timeout)
      .withQueryString("ids" -> ids.mkString(","))
      .get()
      .map(parse[List[FundraiseLoan]])
  }

  def create(fundraise_loan_creation: FundraiseLoanCreation, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseLoan]] = {
    ws.url(s"$endPoint/fundraise", timeout).put(Json.toJson(fundraise_loan_creation)).map(parse[FundraiseLoan])
  }

  def update(id: String, fundraise_loan_edition: FundraiseLoanEdition, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseLoan]] = {
    ws.url(s"$endPoint/fundraise/$id", timeout).post(Json.toJson(fundraise_loan_edition)).map(parse[FundraiseLoan])
  }

  def updateRunning(id: String, fundraise_loan_running_edition: FundraiseLoanRunningEdition, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseLoan]] = {
    ws.url(s"$endPoint/fundraise/running/$id", timeout).post(Json.toJson(fundraise_loan_running_edition)).map(parse[FundraiseLoan])
  }

  def search(criteria: FundraiseLoanSearch, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[FundraiseLoan]]] = {
    ws.url(s"$endPoint/fundraises", timeout)
      .withQueryString(LangUtils.productToQueryString(criteria): _*)
      .get
      .map(parse[PaginatedSequence[FundraiseLoan]])
  }

  def submit(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseLoan]] = {
    ws.url(s"$endPoint/fundraise/$id/submit", timeout).post(Results.EmptyContent()).map(parse[FundraiseLoan])
  }

  def reject(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseLoan]] = {
    ws.url(s"$endPoint/fundraise/$id/reject", timeout).post(Results.EmptyContent()).map(parse[FundraiseLoan])
  }

  def launch(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseLoan]] = {
    ws.url(s"$endPoint/fundraise/$id/launch", timeout).post(Results.EmptyContent()).map(parse[FundraiseLoan])
  }

  def success(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseLoan]] = {
    ws.url(s"$endPoint/fundraise/$id/close", timeout).post(Results.EmptyContent()).map(parse[FundraiseLoan])
  }

  def refund(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseLoan]] = {
    ws.url(s"$endPoint/fundraise/$id/refund", timeout).post(Results.EmptyContent()).map(parse[FundraiseLoan])
  }

  def getLenderRepaymentScheduleEstimation(
    id:             String,
    repayment_info: RepaymentInfo,
    timeout:        Long          = -1
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[RepaymentWithDate]]] = {
    ws.url(s"$endPoint/fundraise/$id/info/estimate/lender", timeout).post(Json.toJson(repayment_info)).map(parse[List[RepaymentWithDate]])
  }

  def getBorrowerRepaymentScheduleEstimation(
    estimate_borrower_info: EstimateBorrowerInfo,
    timeout:                Long                 = -1
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[RepaymentWithDate]]] = {
    ws.url(s"$endPoint/fundraise/info/estimate/borrower", timeout).post(Json.toJson(estimate_borrower_info)).map(parse[List[RepaymentWithDate]])
  }

  def getLenderRepaymentSchedule(id: String, user_id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[RepaymentWithDate]]] = {
    ws.url(s"$endPoint/fundraise/$id/info/user/$user_id", timeout).post(Results.EmptyContent()).map(parse[List[RepaymentWithDate]])
  }

  def getBorrowerRepaymentSchedule(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[RepaymentWithDate]]] = {
    ws.url(s"$endPoint/fundraise/$id/info/borrower", timeout).post(Results.EmptyContent()).map(parse[List[RepaymentWithDate]])
  }

  def getBorrowerRepaymentScheduleDetail(id: String, payment_month: Int, payment_year: Int, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[RepaymentDetail]]] = {
    ws.url(s"$endPoint/fundraise/$id/detail/borrower/$payment_month/$payment_year", timeout).get().map(parse[List[RepaymentDetail]])
  }

  def generateRepaymentSchedule(
    id:      String,
    timeout: Long   = -1
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[ScheduledPayment]]] = {
    ws.url(s"$endPoint/fundraise/$id/schedule/define", timeout).post(Results.EmptyContent()).map(parse[List[ScheduledPayment]])
  }

  def generateCustomRepaymentSchedule(id: String, repayment_info_vector: RepaymentInfoVector,
                                      timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[ScheduledPayment]]] = {
    ws.url(s"$endPoint/fundraise/$id/schedule/custom", timeout).post(Json.toJson(repayment_info_vector)).map(parse[List[ScheduledPayment]])
  }

  def cancelRemainingPayments(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[ScheduledPayment]]] = {
    ws.url(s"$endPoint/fundraise/$id/schedule/cancel-all", timeout).post(Results.EmptyContent()).map(parse[List[ScheduledPayment]])
  }

  def allLendsOnFundraise(
    id:             String,
    criteria:       TransactionSearch,
    table_criteria: TableSearch,
    timeout:        Long              = -1
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[Lend]]] = {
    ws.url(s"$endPoint/fundraise/$id/lends", timeout)
      .withQueryString(LangUtils.productToQueryString(criteria): _*)
      .withQueryString(LangUtils.productToQueryString(table_criteria): _*)
      .get
      .map(parse[PaginatedSequence[Lend]])
  }

  def allLendsByUser(
    user_id:        String,
    criteria:       TransactionSearch,
    table_criteria: TableSearch,
    timeout:        Long              = -1
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[Transaction]]] = {
    ws.url(s"$endPoint/fundraise/lends/user/$user_id", timeout)
      .withQueryString(LangUtils.productToQueryString(criteria): _*)
      .withQueryString(LangUtils.productToQueryString(table_criteria): _*)
      .get
      .map(parse[PaginatedSequence[Transaction]])
  }

  def lend(id: String, lend_creation: LendCreation, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Transaction]] = {
    ws.url(s"$endPoint/fundraise/$id/lend", timeout).post(Json.toJson(lend_creation)).map(parse[Transaction])
  }

  def importFromCsv(csv: MultipartFormData[TemporaryFile], timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, ImportResult[FundraiseLoan]]] = {
    ws.postFile(s"$endPoint_import/fundraise-loan/csv", csv, List(), timeout).map(parse[ImportResult[FundraiseLoan]])
  }
}
