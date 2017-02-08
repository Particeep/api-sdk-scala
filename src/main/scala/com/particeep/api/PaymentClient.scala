package com.particeep.api

import com.particeep.api.core.{ResponseParser, WSClient}
import com.particeep.api.models.{ErrorResult, PaginatedSequence}
import com.particeep.api.models.fundraise.loan.{ScheduledPayment, ScheduledPaymentSearch}
import com.particeep.api.models.payment.{PayResult, PaymentCbCreation, ScheduledPaymentCreation}
import com.particeep.api.models.transaction.Transaction
import com.particeep.api.utils.LangUtils
import play.api.libs.json.Json
import play.api.mvc.Results

import scala.concurrent.{ExecutionContext, Future}

trait PaymentCapability {
  self: WSClient =>

  val payment: PaymentClient = new PaymentClient(this)
}

class PaymentClient(ws: WSClient) extends ResponseParser {

  private[this] val endPoint: String = "/payment"
  implicit lazy val pay_result_format = PayResult.format
  implicit lazy val payment_cb_creation_format = PaymentCbCreation.format
  implicit lazy val transaction_format = Transaction.format
  implicit lazy val scheduled_payment_creation_format = ScheduledPaymentCreation.format
  implicit lazy val scheduled_payment_format = ScheduledPayment.format

  def payment(transaction_id: String, payment_cb_creation: PaymentCbCreation, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PayResult]] = {
    ws.url(s"$endPoint/$transaction_id", timeout).post(Json.toJson(payment_cb_creation)).map(parse[PayResult])
  }

  def offlinePayment(transaction_id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Transaction]] = {
    ws.url(s"$endPoint/offline/$transaction_id", timeout).post(Results.EmptyContent()).map(parse[Transaction])
  }

  def creditCardPayment(transaction_id: String, payment_cb_creation: PaymentCbCreation, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, String]] = {
    ws.url(s"$endPoint/credit-card/$transaction_id", timeout).post(Json.toJson(payment_cb_creation)).map(parse[String])
  }

  def directCashIn(transaction_id: String, payment_cb_creation: PaymentCbCreation, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, String]] = {
    ws.url(s"$endPoint/cash-in/direct/$transaction_id", timeout).post(Json.toJson(payment_cb_creation)).map(parse[String])
  }

  def walletPayment(transaction_id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Transaction]] = {
    ws.url(s"$endPoint/wallet/$transaction_id", timeout).post(Results.EmptyContent()).map(parse[Transaction])
  }

  def refund(transaction_id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Transaction]] = {
    ws.url(s"$endPoint/refund/$transaction_id", timeout).post(Results.EmptyContent()).map(parse[Transaction])
  }

  def addScheduledPayment(scheduled_payment_creations: List[ScheduledPaymentCreation], timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[ScheduledPayment]]] = {
    ws.url(s"$endPoint/schedule/add", timeout).post(Json.toJson(scheduled_payment_creations)).map(parse[List[ScheduledPayment]])
  }

  def scheduledPaymentByIds(ids: List[String], timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[ScheduledPayment]]] = {
    ws.url(s"$endPoint/schedule", timeout)
      .withQueryString("ids" -> ids.mkString(","))
      .get()
      .map(parse[List[ScheduledPayment]])
  }

  def searchScheduledPayments(criteria: ScheduledPaymentSearch, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[ScheduledPayment]]] = {
    ws.url(s"$endPoint/schedule/search", timeout)
      .withQueryString(LangUtils.productToQueryString(criteria): _*)
      .get
      .map(parse[PaginatedSequence[ScheduledPayment]])
  }

  def cancelScheduledPayment(ids: List[String], timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[ScheduledPayment]]] = {
    ws.url(s"$endPoint/schedule/cancel", timeout)
      .withQueryString("ids" -> ids.mkString(","))
      .get()
      .map(parse[List[ScheduledPayment]])
  }
}
