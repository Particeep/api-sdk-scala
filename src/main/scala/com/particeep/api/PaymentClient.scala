package com.particeep.api

import com.particeep.api.core._
import com.particeep.api.models.{ ErrorResult, PaginatedSequence, TableSearch }
import com.particeep.api.models.fundraise.loan.{ ScheduledPayment, ScheduledPaymentSearch }
import com.particeep.api.models.payment.{ PayResult, PaymentCbCreation, ScheduledPaymentCreation }
import com.particeep.api.models.transaction.Transaction
import com.particeep.api.utils.LangUtils
import play.api.libs.json.Json

import scala.concurrent.{ ExecutionContext, Future }

trait PaymentCapability {
  self: WSClient =>

  val payment: PaymentClient = new PaymentClient(this)
  def payment(credentials: ApiCredential): PaymentClient = new PaymentClient(this, Some(credentials))
}

object PaymentClient {

  private val endPoint: String = "/payment"
  private implicit lazy val pay_result_format = PayResult.format
  private implicit lazy val payment_cb_creation_format = PaymentCbCreation.format
  private implicit lazy val transaction_format = Transaction.format
  private implicit lazy val scheduled_payment_creation_format = ScheduledPaymentCreation.format
  private implicit lazy val scheduled_payment_format = ScheduledPayment.format

}

class PaymentClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends WithWS with WithCredentials with EntityClient {

  import PaymentClient._

  def payment(transaction_id: String, payment_cb_creation: PaymentCbCreation, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PayResult]] = {
    ws.post[PayResult](s"$endPoint/$transaction_id", timeout, Json.toJson(payment_cb_creation))
  }

  def offlinePayment(transaction_id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PayResult]] = {
    ws.post[PayResult](s"$endPoint/offline/$transaction_id", timeout, Json.toJson(""))
  }

  def creditCardPayment(transaction_id: String, payment_cb_creation: PaymentCbCreation, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PayResult]] = {
    ws.post[PayResult](s"$endPoint/credit-card/$transaction_id", timeout, Json.toJson(payment_cb_creation))
  }

  def directCashIn(transaction_id: String, payment_cb_creation: PaymentCbCreation, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PayResult]] = {
    ws.post[PayResult](s"$endPoint/cash-in/direct/$transaction_id", timeout, Json.toJson(payment_cb_creation))
  }

  def walletPayment(transaction_id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PayResult]] = {
    ws.post[PayResult](s"$endPoint/wallet/$transaction_id", timeout, Json.toJson(""))
  }

  def refund(transaction_id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Transaction]] = {
    ws.post[Transaction](s"$endPoint/refund/$transaction_id", timeout, Json.toJson(""))
  }

  def addScheduledPayment(scheduled_payment_creations: List[ScheduledPaymentCreation], timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[ScheduledPayment]]] = {
    ws.post[List[ScheduledPayment]](s"$endPoint/schedule/add", timeout, Json.toJson(scheduled_payment_creations))
  }

  def scheduledPaymentByIds(ids: List[String], timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[ScheduledPayment]]] = {
    ws.get[List[ScheduledPayment]](s"$endPoint/schedule", timeout, List("ids" -> ids.mkString(",")))
  }

  def searchScheduledPayments(
    criteria:       ScheduledPaymentSearch,
    table_criteria: TableSearch,
    timeout:        Long                   = defaultTimeOut
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[ScheduledPayment]]] = {
    ws.get[PaginatedSequence[ScheduledPayment]](
      s"$endPoint/schedule/search",
      timeout,
      LangUtils.productToQueryString(criteria) ++ LangUtils.productToQueryString(table_criteria)
    )
  }

  def cancelScheduledPayment(ids: List[String], timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[ScheduledPayment]]] = {
    ws.get[List[ScheduledPayment]](s"$endPoint/schedule/cancel", timeout, List("ids" -> ids.mkString(",")))
  }
}
