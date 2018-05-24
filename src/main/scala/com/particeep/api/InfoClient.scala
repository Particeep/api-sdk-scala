package com.particeep.api

import com.particeep.api.core._
import com.particeep.api.models._
import play.api.libs.json._

import scala.concurrent.{ ExecutionContext, Future }

case class Info(version: String, debugEnable: Boolean, metaEnable: Boolean)

object InfoClient {
  private val endPoint: String = "/info"
  private implicit val format = Json.format[Info]
}

trait InfoCapability {
  self: WSClient =>

  val info: InfoClient = new InfoClient(this)
  def info(credentials: ApiCredential): InfoClient = new InfoClient(this, Some(credentials))
}

class InfoClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends WithWS with WithCredentials with EntityClient {

  import InfoClient._

  def info(timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Info]] = {
    ws.get[Info](endPoint, timeout)
  }
}
