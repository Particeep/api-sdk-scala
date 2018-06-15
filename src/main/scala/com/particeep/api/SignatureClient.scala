package com.particeep.api

import com.particeep.api.core._
import com.particeep.api.models.{ ErrorResult, PaginatedSequence, TableSearch }
import com.particeep.api.models.signature._
import com.particeep.api.utils.LangUtils
import play.api.libs.iteratee.Enumerator
import play.api.libs.json.Json

import scala.concurrent.{ ExecutionContext, Future }

trait SignatureCapability {
  self: WSClient =>

  val signature: SignatureClient = new SignatureClient(this)
  def signature(credentials: ApiCredential): SignatureClient = new SignatureClient(this, Some(credentials))
}

object SignatureClient {

  private val endPoint: String = "/signature"
  private implicit val format = Signature.format
  private implicit val multiple_format = SignatureMultiple.format
  private implicit val creation_format = SignatureCreation.format
  private implicit val multiple_creation_format = SignatureMultipleCreation.format
  private implicit val signature_data_format = SignatureData.format

}

class SignatureClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends WithWS with WithCredentials with EntityClient {

  import SignatureClient._

  def sign(signature_creation: SignatureCreation, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Signature]] = {
    ws.post[Signature](s"$endPoint", timeout, Json.toJson(signature_creation))
  }

  def signMultiple(signature_multiple_creation: SignatureMultipleCreation, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, SignatureMultiple]] = {
    ws.post[SignatureMultiple](s"$endPoint/multiple", timeout, Json.toJson(signature_multiple_creation))
  }

  def byId(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Signature]] = {
    ws.get[Signature](s"$endPoint/$id", timeout)
  }

  def byIds(ids: List[String], timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[Signature]]] = {
    ws.get[List[Signature]](s"$endPoint", timeout, List("ids" -> ids.mkString(",")))
  }

  def multipleById(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, SignatureMultiple]] = {
    ws.get[SignatureMultiple](s"$endPoint/multiple/$id", timeout)
  }

  def search(
    criteria:       SignatureSearch,
    table_criteria: TableSearch,
    timeout:        Long            = defaultTimeOut
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[SignatureData]]] = {
    ws.get[PaginatedSequence[SignatureData]](s"$endPoint/search", timeout, LangUtils.productToQueryString(criteria) ++ LangUtils.productToQueryString(table_criteria))
  }

  def getFile(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Enumerator[Array[Byte]]]] = {
    ws.getStream(s"$endPoint/file/$id", timeout)
  }

  def getStatus(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, String]] = {
    ws.get[String](s"$endPoint/status/$id", timeout)
  }

  def delete(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Signature]] = {
    ws.delete[Signature](s"$endPoint/$id", timeout)
  }
}
