package com.particeep.api

import com.particeep.api.core.{ResponseParser, WSClient}
import com.particeep.api.models.ErrorResult
import com.particeep.api.models.payment.{PayResult, PaymentCbCreation}
import com.particeep.api.models.transaction.Transaction
import play.api.libs.json.Json
import play.api.mvc.Results

import scala.concurrent.{ExecutionContext, Future}

trait PaymentCapability {
  self: WSClient =>

  val form: PaymentClient = new PaymentClient(this)
}

class PaymentClient(ws: WSClient) extends ResponseParser {

  private val endPoint: String = "/payment"
  implicit val pay_result_format = PayResult.format
  implicit val payment_cb_creation_format = PaymentCbCreation.format
  implicit val transaction_format = Transaction.format

  def payment(transaction_id: String, payment_cb_creation: PaymentCbCreation, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PayResult]] = {
    ws.url(s"$endPoint/$transaction_id", timeout).post(Json.toJson(payment_cb_creation)).map(parse[PayResult])
  }

  def offlinePayment(transaction_id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Transaction]] = {
    ws.url(s"$endPoint/offline/$transaction_id").post(Results.EmptyContent()).map(parse[Transaction])
  }

  def creditCardPayment(transaction_id: String, payment_cb_creation: PaymentCbCreation, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, String]] = {
    ws.url(s"$endPoint/credit-card/$transaction_id").post(Json.toJson(payment_cb_creation)).map(parse[String])
  }

  def directCashIn(transaction_id: String, payment_cb_creation: PaymentCbCreation, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, String]] = {
    ws.url(s"$endPoint/cash-in/direct/$transaction_id").post(Json.toJson(payment_cb_creation)).map(parse[String])
  }

  def walletPayment(transaction_id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Transaction]] = {
    ws.url(s"$endPoint/wallet/$transaction_id").post(Results.EmptyContent()).map(parse[Transaction])
  }

  def refund(transaction_id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Transaction]] = {
    ws.url(s"$endPoint/refund/$transaction_id").post(Results.EmptyContent()).map(parse[Transaction])
  }
}
