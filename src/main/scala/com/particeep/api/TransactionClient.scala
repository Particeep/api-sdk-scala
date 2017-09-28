package com.particeep.api

import com.particeep.api.core._
import com.particeep.api.models.imports.ImportResult
import com.particeep.api.models.{ ErrorResult, PaginatedSequence, TableSearch }
import com.particeep.api.models.transaction._
import com.particeep.api.utils.LangUtils
import play.api.libs.Files.TemporaryFile
import play.api.libs.json.Json
import play.api.mvc.MultipartFormData

import scala.concurrent.{ ExecutionContext, Future }

trait TransactionCapability {
  self: WSClient =>

  val transaction: TransactionClient = new TransactionClient(this)
  def transaction(credentials: ApiCredential): TransactionClient = new TransactionClient(this, Some(credentials))
}

object TransactionClient {
  private val endPoint: String = "/transaction"
  private val endPoint_import: String = "/import"
  private implicit val format = Transaction.format
  private implicit val creationFormat = TransactionCreation.format
  private implicit val editionFormat = TransactionEdition.format
  private implicit val transactionDataFormat = TransactionData.format
  private implicit val importResultReads = ImportResult.format[Transaction]
}

class TransactionClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends ResponseParser with WithWS with WithCredentials with EntityClient {

  import TransactionClient._

  def byId(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Transaction]] = {
    ws.url(s"$endPoint/$id", timeout).get().map(parse[Transaction])
  }

  def byIds(ids: List[String], timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[Transaction]]] = {
    ws.url(s"$endPoint", timeout)
      .withQueryString("ids" -> ids.mkString(","))
      .get()
      .map(parse[List[Transaction]])
  }

  def create(transaction_creation: TransactionCreation, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Transaction]] = {
    ws.url(s"$endPoint", timeout).put(Json.toJson(transaction_creation)).map(parse[Transaction])
  }

  def update(id: String, transaction_edition: TransactionEdition, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Transaction]] = {
    ws.url(s"$endPoint/$id", timeout).post(Json.toJson(transaction_edition)).map(parse[Transaction])
  }

  def search(criteria: TransactionSearch, table_criteria: TableSearch, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[TransactionData]]] = {
    ws.url(s"$endPoint/search", timeout)
      .withQueryString(LangUtils.productToQueryString(criteria): _*)
      .withQueryString(LangUtils.productToQueryString(table_criteria): _*)
      .get
      .map(parse[PaginatedSequence[TransactionData]])
  }

  def cancel(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Transaction]] = {
    ws.url(s"$endPoint/$id/cancel", timeout).delete().map(parse[Transaction])
  }

  def importFromCsv(csv: MultipartFormData[TemporaryFile], timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, ImportResult[Transaction]]] = {
    ws.postFile(s"$endPoint_import/transaction/csv", csv, List(), timeout).map(parse[ImportResult[Transaction]])
  }
}
