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

  implicit val format = FundraiseLoan.format
  implicit val creation_format = FundraiseLoanCreation.format
  implicit val edition_format = FundraiseLoanEdition.format
  implicit val repayment_info_format = RepaymentInfo.format
  implicit val repayment_with_date_format = RepaymentWithDate.format
  implicit val scheduled_payment_format = ScheduledPayment.format

  def byId(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseLoan]] = {
    ws.url(s"/loan/fundraise/$id", timeout).get().map(parse[FundraiseLoan])
  }

  def byIds(ids: Seq[String], timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Seq[FundraiseLoan]]] = {
    ws.url(s"/loan/fundraise", timeout)
      .withQueryString("ids" -> ids.mkString(","))
      .get()
      .map(parse[Seq[FundraiseLoan]])
  }

  def create(fundraise_loan_creation: FundraiseLoanCreation, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseLoan]] = {
    ws.url(s"/loan/fundraise", timeout).put(Json.toJson(fundraise_loan_creation)).map(parse[FundraiseLoan])
  }

  def update(id: String, fundraise_loan_edition: FundraiseLoanEdition, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseLoan]] = {
    ws.url(s"/loan/fundraise/$id", timeout).post(Json.toJson(fundraise_loan_edition)).map(parse[FundraiseLoan])
  }

  def submit(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseLoan]] = {
    ws.url(s"/loan/fundraise/$id/submit", timeout).post(Results.EmptyContent()).map(parse[FundraiseLoan])
  }

  def reject(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseLoan]] = {
    ws.url(s"/loan/fundraise/$id/reject", timeout).post(Results.EmptyContent()).map(parse[FundraiseLoan])
  }

  def launch(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseLoan]] = {
    ws.url(s"/loan/fundraise/$id/launch", timeout).post(Results.EmptyContent()).map(parse[FundraiseLoan])
  }

  def success(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseLoan]] = {
    ws.url(s"/loan/fundraise/$id/close", timeout).post(Results.EmptyContent()).map(parse[FundraiseLoan])
  }

  def refund(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FundraiseLoan]] = {
    ws.url(s"/loan/fundraise/$id/refund", timeout).post(Results.EmptyContent()).map(parse[FundraiseLoan])
  }

  def getLenderRepaymentScheduleEstimation(id: String, repayment_info: RepaymentInfo, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Seq[RepaymentWithDate]]] = {
    ws.url(s"/loan/fundraise/$id/info/estimate/lender", timeout).post(Json.toJson(repayment_info)).map(parse[Seq[RepaymentWithDate]])
  }

  def getBorrowerRepaymentScheduleEstimation(id: String, repayment_info: RepaymentInfo, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Seq[RepaymentWithDate]]] = {
    ws.url(s"/loan/fundraise/$id/info/estimate/borrower", timeout).post(Json.toJson(repayment_info)).map(parse[Seq[RepaymentWithDate]])
  }

  def getLenderRepaymentSchedule(id: String, user_id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Seq[RepaymentWithDate]]] = {
    ws.url(s"/fundraise/$id/info/user/$user_id", timeout).post(Results.EmptyContent()).map(parse[Seq[RepaymentWithDate]])
  }

  def getBorrowerRepaymentSchedule(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Seq[RepaymentWithDate]]] = {
    ws.url(s"/fundraise/$id/info/borrower", timeout).post(Results.EmptyContent()).map(parse[Seq[RepaymentWithDate]])
  }

  def generateRepaymentSchedule(id: String, repayment_info: RepaymentInfo, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Seq[ScheduledPayment]]] = {
    ws.url(s"/fundraise/$id/schedule/define", timeout).post(Json.toJson(repayment_info)).map(parse[Seq[ScheduledPayment]])
  }
}
