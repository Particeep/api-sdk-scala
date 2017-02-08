package com.particeep.api

import com.particeep.api.core.{ResponseParser, WSClient}
import com.particeep.api.models.{ErrorResult, PaginatedSequence}
import com.particeep.api.models.transaction.{Transaction, TransactionSearch}
import com.particeep.api.utils.LangUtils

import scala.concurrent.{ExecutionContext, Future}

trait TransactionCapability {
  self: WSClient =>

  val transaction: TransactionClient = new TransactionClient(this)
}

class TransactionClient(ws: WSClient) extends ResponseParser {

  private[this] val endPoint: String = "/transaction"
  implicit val format = Transaction.format

  def byId(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Transaction]] = {
    ws.url(s"$endPoint/$id", timeout).get().map(parse[Transaction])
  }

  def byIds(ids: List[String], timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[Transaction]]] = {
    ws.url(s"$endPoint", timeout)
      .withQueryString("ids" -> ids.mkString(","))
      .get()
      .map(parse[List[Transaction]])
  }

  def search(criteria: TransactionSearch, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[Transaction]]] = {
    ws.url(s"$endPoint/search", timeout)
      .withQueryString(LangUtils.productToQueryString(criteria): _*)
      .get
      .map(parse[PaginatedSequence[Transaction]])
  }

  def cancel(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Transaction]] = {
    ws.url(s"$endPoint/$id/cancel", timeout).delete().map(parse[Transaction])
  }
}
