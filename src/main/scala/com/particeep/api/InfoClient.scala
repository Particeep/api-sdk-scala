package com.particeep.api

import com.particeep.api.core.{ ApiCredential, ResponseParser, WSClient }
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
}

class InfoClient(ws: WSClient) extends ResponseParser {

  import InfoClient._

  def info(timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, Info]] = {
    ws.url(endPoint, timeout).get().map(parse[Info])
  }
}
