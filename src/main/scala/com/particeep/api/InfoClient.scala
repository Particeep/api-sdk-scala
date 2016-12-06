package com.particeep.api

import com.particeep.api.core.{ResponseParser, WSClient}
import com.particeep.api.models._
import play.api.libs.json._

import scala.concurrent.{ExecutionContext, Future}

case class Info(version: String, debugEnable: Boolean, metaEnable: Boolean)

trait InfoClient extends ResponseParser[Info] {
  self: WSClient =>

  private val endPoint: String = "/info"
  implicit val format = Info.format

  def info(timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Info]] = {
    url(endPoint, timeout).get().map(parse)
  }
}

object Info {
  implicit val format = Json.format[Info]
}
