package com.particeep.api

import com.particeep.api.core.{ ApiCredential, ResponseParser, WSClient }
import com.particeep.api.models.{ ErrorResult, PaginatedSequence }
import com.particeep.api.models.signature.{ Signature, SignatureCreation, SignatureSearch }
import com.particeep.api.utils.LangUtils
import play.api.libs.json.Json

import scala.concurrent.{ ExecutionContext, Future }

trait SignatureCapability {
  self: WSClient =>

  val signature: SignatureClient = new SignatureClient(this)
}

object SignatureClient {

  private val endPoint: String = "/signature"
  private implicit val format = Signature.format
  private implicit val creation_format = SignatureCreation.format

}

class SignatureClient(ws: WSClient) extends ResponseParser {

  import SignatureClient._

  def sign(signature_creation: SignatureCreation, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, Signature]] = {
    ws.url(s"$endPoint", timeout).post(Json.toJson(signature_creation)).map(parse[Signature])
  }

  def byId(id: String, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, Signature]] = {
    ws.url(s"$endPoint/$id", timeout).get().map(parse[Signature])
  }

  def byIds(ids: List[String], timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, List[Signature]]] = {
    ws.url(s"$endPoint", timeout)
      .withQueryString("ids" -> ids.mkString(","))
      .get()
      .map(parse[List[Signature]])
  }

  def search(criteria: SignatureSearch, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, PaginatedSequence[Signature]]] = {
    ws.url(s"$endPoint/search", timeout)
      .withQueryString(LangUtils.productToQueryString(criteria): _*)
      .get
      .map(parse[PaginatedSequence[Signature]])
  }

  def getFile(id: String, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, Stream[Byte]]] = {
    ws.url(s"$endPoint/file/$id").get().map(parse[Stream[Byte]])
  }

  def getStatus(id: String, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, String]] = {
    ws.url(s"$endPoint/status/$id").get().map(parse[String])
  }
}
