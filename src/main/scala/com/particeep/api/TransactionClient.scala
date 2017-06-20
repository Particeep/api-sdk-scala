package com.particeep.api

import com.particeep.api.core.{ ApiCredential, ResponseParser, WSClient }
import com.particeep.api.models.{ ErrorResult, PaginatedSequence }
import com.particeep.api.models.transaction.{ Transaction, TransactionCreation, TransactionEdition, TransactionSearch }
import com.particeep.api.utils.LangUtils
import play.api.libs.json.Json

import scala.concurrent.{ ExecutionContext, Future }

trait TransactionCapability {
  self: WSClient =>

  val transaction: TransactionClient = new TransactionClient(this)
}

object TransactionClient {
  private val endPoint: String = "/transaction"
  private implicit val format = Transaction.format
  private implicit val creationFormat = TransactionCreation.format
  private implicit val editionFormat = TransactionEdition.format
}

class TransactionClient(ws: WSClient) extends ResponseParser {

  import TransactionClient._

  def byId(id: String, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, Transaction]] = {
    ws.url(s"$endPoint/$id", timeout).get().map(parse[Transaction])
  }

  def byIds(ids: List[String], timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, List[Transaction]]] = {
    ws.url(s"$endPoint", timeout)
      .withQueryString("ids" -> ids.mkString(","))
      .get()
      .map(parse[List[Transaction]])
  }

  def create(transaction_creation: TransactionCreation, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, Transaction]] = {
    ws.url(s"$endPoint", timeout).put(Json.toJson(transaction_creation)).map(parse[Transaction])
  }

  def update(id: String, transaction_edition: TransactionEdition, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, Transaction]] = {
    ws.url(s"$endPoint/$id", timeout).post(Json.toJson(transaction_edition)).map(parse[Transaction])
  }

  def search(criteria: TransactionSearch, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, PaginatedSequence[Transaction]]] = {
    ws.url(s"$endPoint/search", timeout)
      .withQueryString(LangUtils.productToQueryString(criteria): _*)
      .get
      .map(parse[PaginatedSequence[Transaction]])
  }

  def cancel(id: String, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, Transaction]] = {
    ws.url(s"$endPoint/$id/cancel", timeout).delete().map(parse[Transaction])
  }
}
