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

class WebHookClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends WithWS with WithCredentials with EntityClient {

  import WebHookClient._

  def all(timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[WebHook]]] = {
    ws.get[List[WebHook]](s"$endPoint/all", timeout)
  }

  def create(webhook_creation: WebHookSimple, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, WebHook]] = {
    ws.put[WebHook](s"$endPoint", timeout, Json.toJson(webhook_creation))
  }

  def edition(id: String, webhook_edition: WebHookSimple, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, WebHook]] = {
    ws.post[WebHook](s"$endPoint/$id", timeout, Json.toJson(webhook_edition))
  }

  def delete(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, WebHook]] = {
    ws.delete[WebHook](s"$endPoint/$id", timeout)
  }
}
