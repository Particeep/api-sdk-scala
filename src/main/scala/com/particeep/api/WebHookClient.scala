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

object WebHookClient {
  private val endPoint: String = "/webhook"

  private implicit val format = WebHook.format
  private implicit val simple_format = WebHookSimple.format

}

class WebHookClient(ws: WSClient) extends ResponseParser {

  import WebHookClient._

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
