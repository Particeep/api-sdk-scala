package com.particeep.api

import com.particeep.api.core.WSClient
import play.api.libs.json._
import play.api.libs.ws.WSResponse

import scala.concurrent.{ExecutionContext, Future}

case class Info(version: String, debugEnable: Boolean, metaEnable: Boolean)

trait InfoClient {
  self: WSClient =>

  private val endPoint: String = "/info"

  def info(timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[JsError, Info]] = {
    url(endPoint, timeout).get().map(parseResponse)
  }

  private def parseResponse(response: WSResponse): Either[JsError, Info] = {
    implicit val format = Info.format

    response.json.validate[Info] match {
      case result: JsSuccess[Info] => Right(result.get)
      case err: JsError            => Left(err)
    }
  }
}

object Info {
  implicit val format = Json.format[Info]
}
