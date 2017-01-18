package com.particeep.api

import com.particeep.api.core.{ResponseParser, WSClient}
import com.particeep.api.models.ErrorResult
import com.particeep.api.models.fundraise.loan._
import play.api.libs.json.Json
import play.api.mvc.Results

import scala.concurrent.{ExecutionContext, Future}

trait FundraiseLoanCapability {
  self: WSClient =>

  val fundraise_loan: FundraiseLoanClient = new FundraiseLoanClient(this)
}

class FundraiseLoanClient(ws: WSClient) extends ResponseParser {

  private val endPoint: String = "/loan"
  implicit val format = FundraiseLoan.format
  implicit val creation_format = FundraiseLoanCreation.format
  implicit val edition_format = FundraiseLoanEdition.format
  implicit val repayment_info_format = RepaymentInfo.format
  implicit val repayment_with_date_format = RepaymentWithDate.format
  implicit val repayment_info_vector_format = RepaymentInfoVector.format
  implicit val scheduled_payment_format = ScheduledPayment.format

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

  def submit(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseLoan]] = {
    ws.url(s"$endPoint/fundraise/$id/submit", timeout).post(Results.EmptyContent()).map(parse[FundraiseLoan])
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

  def getLenderRepaymentScheduleEstimation(id: String, repayment_info: RepaymentInfo, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[RepaymentWithDate]]] = {
    ws.url(s"$endPoint/fundraise/$id/info/estimate/lender", timeout).post(Json.toJson(repayment_info)).map(parse[List[RepaymentWithDate]])
  }

  def getBorrowerRepaymentScheduleEstimation(id: String, repayment_info: RepaymentInfo, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[RepaymentWithDate]]] = {
    ws.url(s"$endPoint/fundraise/$id/info/estimate/borrower", timeout).post(Json.toJson(repayment_info)).map(parse[List[RepaymentWithDate]])
  }

  def getLenderRepaymentSchedule(id: String, user_id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[RepaymentWithDate]]] = {
    ws.url(s"$endPoint/fundraise/$id/info/user/$user_id", timeout).post(Results.EmptyContent()).map(parse[List[RepaymentWithDate]])
  }

  def getBorrowerRepaymentSchedule(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[RepaymentWithDate]]] = {
    ws.url(s"$endPoint/fundraise/$id/info/borrower", timeout).post(Results.EmptyContent()).map(parse[List[RepaymentWithDate]])
  }

  def generateRepaymentSchedule(id: String, repayment_info: RepaymentInfo, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[ScheduledPayment]]] = {
    ws.url(s"$endPoint/fundraise/$id/schedule/define", timeout).post(Json.toJson(repayment_info)).map(parse[List[ScheduledPayment]])
  }

  def generateCustomRepaymentSchedule(id: String, repayment_info_vector: RepaymentInfoVector,
                                      timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[ScheduledPayment]]] = {
    ws.url(s"$endPoint/fundraise/$id/schedule/custom", timeout).post(Json.toJson(repayment_info_vector)).map(parse[List[ScheduledPayment]])
  }

  def cancelRemainingPayments(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[ScheduledPayment]]] = {
    ws.url(s"$endPoint/fundraise/$id/schedule/cancel-all", timeout).post(Results.EmptyContent()).map(parse[List[ScheduledPayment]])
  }
}
