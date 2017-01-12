package com.particeep.api

import com.particeep.api.core.{ResponseParser, WSClient}
import com.particeep.api.models.ErrorResult
import com.particeep.api.models.signature.{Signature, SignatureCreation}
import play.api.libs.json.Json

import scala.concurrent.{ExecutionContext, Future}

trait SignatureCapability {
  self: WSClient =>

  val signature: SignatureClient = new SignatureClient(this)
}

class SignatureClient(ws: WSClient) extends ResponseParser {

  private val endPoint: String = "/signature"
  implicit val format = Signature.format
  implicit val creation_format = SignatureCreation.format

  def sign(signature_creation: SignatureCreation, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Signature]] = {
    ws.url(s"$endPoint", timeout).post(Json.toJson(signature_creation)).map(parse[Signature])
  }

  def byId(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Signature]] = {
    ws.url(s"$endPoint/$id", timeout).get().map(parse[Signature])
  }

  def getFile(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Stream[Byte]]] = {
    ws.url(s"$endPoint/file/$id").get().map(parse[Stream[Byte]])
  }

  def getStatus(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, String]] = {
    ws.url(s"$endPoint/status/$id").get().map(parse[String])
  }
}