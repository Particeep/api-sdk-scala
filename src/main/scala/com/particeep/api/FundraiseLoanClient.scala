package com.particeep.api

import java.io.File

import com.particeep.api.core._
import com.particeep.api.models.{ ErrorResult, PaginatedSequence, TableSearch }
import com.particeep.api.models.fundraise.loan._
import com.particeep.api.models.imports.ImportResult
import com.particeep.api.models.transaction.{ Transaction, TransactionSearch }
import com.particeep.api.models.user.User
import com.particeep.api.utils.LangUtils
import play.api.libs.json.Json

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
  private implicit val loan_repayment_schedule_format = LoanRepaymentSchedule.format
  private implicit val loan_repayment_schedule_and_user_format = LoanRepaymentSchedule.loan_repayment_schedule_and_user_format
  private implicit val scheduled_payment_format = ScheduledPayment.format
  private implicit val lend_format = Lend.format
  private implicit val transaction_format = Transaction.format
  private implicit val lend_creation_format = LendCreation.format
  private implicit val estimate_borrower_info_format = EstimateBorrowerInfo.format
  private implicit val importResultReads = ImportResult.format[FundraiseLoan]
}

class FundraiseLoanClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends WithWS with WithCredentials with EntityClient {

  import FundraiseLoanClient._

  def byId(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseLoan]] = {
    ws.get[FundraiseLoan](s"$endPoint/fundraise/$id", timeout)
  }

  def byIds(ids: Seq[String], timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[FundraiseLoan]]] = {
    ws.get[List[FundraiseLoan]](s"$endPoint/fundraise", timeout, List("ids" -> ids.mkString(",")))
  }

  def create(fundraise_loan_creation: FundraiseLoanCreation, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseLoan]] = {
    ws.put[FundraiseLoan](s"$endPoint/fundraise", timeout, Json.toJson(fundraise_loan_creation))
  }

  def update(id: String, fundraise_loan_edition: FundraiseLoanEdition, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseLoan]] = {
    ws.post[FundraiseLoan](s"$endPoint/fundraise/$id", timeout, Json.toJson(fundraise_loan_edition))
  }

  def updateRunning(
    id:                             String,
    fundraise_loan_running_edition: FundraiseLoanRunningEdition,
    timeout:                        Long                        = defaultTimeOut
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseLoan]] = {
    ws.post[FundraiseLoan](s"$endPoint/fundraise/running/$id", timeout, Json.toJson(fundraise_loan_running_edition))
  }

  def search(
    criteria:       FundraiseLoanSearch,
    table_criteria: TableSearch,
    timeout:        Long                = defaultTimeOut
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[FundraiseLoan]]] = {
    ws.get[PaginatedSequence[FundraiseLoan]](s"$endPoint/fundraises", timeout, LangUtils.productToQueryString(criteria) ++ LangUtils.productToQueryString(table_criteria))
  }

  def delete(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseLoan]] = {
    ws.delete[FundraiseLoan](s"$endPoint/fundraise/$id", timeout)
  }

  def submit(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseLoan]] = {
    ws.post[FundraiseLoan](s"$endPoint/fundraise/$id/submit", timeout, Json.toJson(""))
  }

  def reject(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseLoan]] = {
    ws.post[FundraiseLoan](s"$endPoint/fundraise/$id/reject", timeout, Json.toJson(""))
  }

  def launch(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseLoan]] = {
    ws.post[FundraiseLoan](s"$endPoint/fundraise/$id/launch", timeout, Json.toJson(""))
  }

  def success(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseLoan]] = {
    ws.post[FundraiseLoan](s"$endPoint/fundraise/$id/close", timeout, Json.toJson(""))
  }

  def refund(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseLoan]] = {
    ws.post[FundraiseLoan](s"$endPoint/fundraise/$id/refund", timeout, Json.toJson(""))
  }

  def getLenderRepaymentScheduleEstimation(
    id:             String,
    repayment_info: RepaymentInfo,
    timeout:        Long          = defaultTimeOut
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[RepaymentWithDate]]] = {
    ws.post[List[RepaymentWithDate]](s"$endPoint/fundraise/$id/info/estimate/lender", timeout, Json.toJson(repayment_info))
  }

  def getBorrowerRepaymentScheduleEstimation(
    estimate_borrower_info: EstimateBorrowerInfo,
    timeout:                Long                 = defaultTimeOut
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[RepaymentWithDate]]] = {
    ws.post[List[RepaymentWithDate]](s"$endPoint/fundraise/info/estimate/borrower", timeout, Json.toJson(estimate_borrower_info))
  }

  def getLenderRepaymentSchedule(id: String, user_id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[RepaymentWithDate]]] = {
    ws.post[List[RepaymentWithDate]](s"$endPoint/fundraise/$id/info/user/$user_id", timeout, Json.toJson(""))
  }

  def getTransactionRepaymentSchedule(id: String, transaction_id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[LoanRepaymentSchedule]]] = {
    ws.get[List[LoanRepaymentSchedule]](s"$endPoint/fundraise/$id/repayment/transaction/$transaction_id", timeout)
  }

  def getBorrowerRepaymentSchedule(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[RepaymentWithDate]]] = {
    ws.post[List[RepaymentWithDate]](s"$endPoint/fundraise/$id/info/borrower", timeout, Json.toJson(""))
  }

  def getBorrowerRepaymentScheduleDetail(
    id:            String,
    payment_day:   Int,
    payment_month: Int,
    payment_year:  Int,
    timeout:       Long   = defaultTimeOut
  )(
    implicit
    exec: ExecutionContext
  ): Future[Either[ErrorResult, List[(LoanRepaymentSchedule, User)]]] = {
    ws.get[List[(LoanRepaymentSchedule, User)]](s"$endPoint/fundraise/$id/detail/borrower/$payment_day/$payment_month/$payment_year", timeout)
  }

  def payOfflineBorrowerRepaymentSchedule(
    id:            String,
    payment_day:   Int,
    payment_month: Int,
    payment_year:  Int,
    timeout:       Long   = defaultTimeOut
  )(
    implicit
    exec: ExecutionContext
  ): Future[Either[ErrorResult, Seq[LoanRepaymentSchedule]]] = {
    ws.post[Seq[LoanRepaymentSchedule]](s"$endPoint/fundraise/$id/pay-offline/borrower/$payment_day/$payment_month/$payment_year", timeout, Json.toJson(""))
  }

  def generateRepaymentSchedule(
    id:      String,
    timeout: Long   = defaultTimeOut
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[ScheduledPayment]]] = {
    ws.post[List[ScheduledPayment]](s"$endPoint/fundraise/$id/schedule/define", timeout, Json.toJson(""))
  }

  def generateCustomRepaymentSchedule(id: String, repayment_info_vector: RepaymentInfoVector,
                                      timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[ScheduledPayment]]] = {
    ws.post[List[ScheduledPayment]](s"$endPoint/fundraise/$id/schedule/update", timeout, Json.toJson(repayment_info_vector))
  }

  def cancelRemainingPayments(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[ScheduledPayment]]] = {
    ws.post[List[ScheduledPayment]](s"$endPoint/fundraise/$id/schedule/cancel-all", timeout, Json.toJson(""))
  }

  def allLendsOnFundraise(
    id:             String,
    criteria:       TransactionSearch,
    table_criteria: TableSearch,
    timeout:        Long              = defaultTimeOut
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[Lend]]] = {
    ws.get[PaginatedSequence[Lend]](s"$endPoint/fundraise/$id/lends", timeout, LangUtils.productToQueryString(criteria) ++ LangUtils.productToQueryString(table_criteria))
  }

  def allLendsByUser(
    user_id:        String,
    criteria:       TransactionSearch,
    table_criteria: TableSearch,
    timeout:        Long              = defaultTimeOut
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[Transaction]]] = {
    ws.get[PaginatedSequence[Transaction]](s"$endPoint/fundraise/lends/user/$user_id", timeout, LangUtils.productToQueryString(criteria) ++ LangUtils.productToQueryString(table_criteria))
  }

  def lend(id: String, lend_creation: LendCreation, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Transaction]] = {
    ws.post[Transaction](s"$endPoint/fundraise/$id/lend", timeout, Json.toJson(lend_creation))
  }

  def importFromCsv(csv: File, timeout: Long = defaultImportTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, ImportResult[FundraiseLoan]]] = {
    ws.postFile[ImportResult[FundraiseLoan]](s"$endPoint_import/fundraise-loan/csv", timeout, csv, "text/csv", List())
  }
}
