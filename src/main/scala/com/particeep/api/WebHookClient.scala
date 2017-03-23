package com.particeep.api

import com.particeep.api.core.{ ApiCredential, ResponseParser, WSClient }
import com.particeep.api.models.ErrorResult
import com.particeep.api.models.webhook.{ WebHook, WebHookSimple }
import play.api.libs.json.Json

import scala.concurrent.{ ExecutionContext, Future }

trait WebHookCapability {
  self: WSClient =>

  val webhook: WebHookClient = new WebHookClient(this)
}

class WebHookClient(ws: WSClient) extends ResponseParser {

  private[this] val endPoint: String = "/webhook"

  implicit private val format = WebHook.format
  implicit private val simple_format = WebHookSimple.format

  def create(webhook_creation: WebHookSimple, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, WebHook]] = {
    ws.url(s"$endPoint", timeout).put(Json.toJson(webhook_creation)).map(parse[WebHook])
  }

  def edition(id: String, webhook_edition: WebHookSimple, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, WebHook]] = {
    ws.url(s"$endPoint/$id", timeout).post(Json.toJson(webhook_edition)).map(parse[WebHook])
  }

  def delete(id: String, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, WebHook]] = {
    ws.url(s"$endPoint/$id", timeout).delete().map(parse[WebHook])
  }
}
