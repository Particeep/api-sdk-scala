package com.particeep.api

import com.particeep.api.core.{ResponseParser, WSClient}
import com.particeep.api.models._
import play.api.libs.json._

import scala.concurrent.{ExecutionContext, Future}
import com.particeep.api.core.Formatter

case class Info(version: String, debugEnable: Boolean, metaEnable: Boolean)

object Info {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  implicit val format = Json.format[Info]
}

trait InfoCapability {
  self: WSClient =>

  val info: InfoClient = new InfoClient(this)
}

class InfoClient(ws: WSClient) extends ResponseParser {

  private val endPoint: String = "/info"
  implicit val format = Info.format

  def info(timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Info]] = {
    ws.url(endPoint, timeout).get().map(parse[Info])
  }
}
