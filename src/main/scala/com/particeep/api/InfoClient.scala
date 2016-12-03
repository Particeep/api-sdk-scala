package com.particeep.api

import play.api.libs.ws.WSResponse
import scala.concurrent.{ExecutionContext, Future}

case class Info(version: String)

trait InfoClient {
  self: WSClient =>

  private val endPoint: String = "/info"

  def info(timeout: Long = -1)(implicit exec: ExecutionContext): Future[Info] = {
    url(endPoint, timeout).get().map(parseResponse)
  }

  private def parseResponse(response: WSResponse): Info = ???
}
