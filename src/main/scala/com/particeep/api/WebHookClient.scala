package com.particeep.api

import com.particeep.api.core._
import com.particeep.api.models.ErrorResult
import com.particeep.api.models.webhook.{ WebHook, WebHookSimple }
import play.api.libs.json.Json

import scala.concurrent.{ ExecutionContext, Future }

trait WebHookCapability {
  self: WSClient =>

  val webhook: WebHookClient = new WebHookClient(this)
  def webhook(credentials: ApiCredential): WebHookClient = new WebHookClient(this, Some(credentials))
}

object WebHookClient {
  private val endPoint: String = "/webhook"

  private implicit val format = WebHook.format
  private implicit val simple_format = WebHookSimple.format

}

class WebHookClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends ResponseParser with WithWS with WithCredentials with EntityClient {

  import WebHookClient._

  def create(webhook_creation: WebHookSimple, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, WebHook]] = {
    ws.url(s"$endPoint", timeout).put(Json.toJson(webhook_creation)).map(parse[WebHook])
  }

  def edition(id: String, webhook_edition: WebHookSimple, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, WebHook]] = {
    ws.url(s"$endPoint/$id", timeout).post(Json.toJson(webhook_edition)).map(parse[WebHook])
  }

  def delete(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, WebHook]] = {
    ws.url(s"$endPoint/$id", timeout).delete().map(parse[WebHook])
  }
}
