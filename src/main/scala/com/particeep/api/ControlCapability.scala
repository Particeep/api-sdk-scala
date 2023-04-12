package com.particeep.api

import com.particeep.api.core.{ ApiCredential, EntityClient, WSClient, WithCredentials, WithWS }
import com.particeep.api.models.{ ErrorResult, PaginatedSequence, TableSearch }
import com.particeep.api.models.control.{ Control, ControlBlockUpdate, ControlCreation, ControlUpdate, ControlView, ControlViewSearchCriteria, EventControl }
import com.particeep.api.utils.LangUtils
import play.api.libs.json.{ JsNull, Json }

import scala.concurrent.{ ExecutionContext, Future }

trait ControlCapability {
  self: WSClient =>

  val control = new ControlClient(this)

  def control(credentials: ApiCredential): ControlClient = new ControlClient(this, Some(credentials))
}

object ControlClient {
  private val endPoint: String = "/control"
}

class ControlClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends WithWS
  with WithCredentials
  with EntityClient {

  import ControlClient._

  def byIds(ids: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[ControlView]]] = {
    ws.get[List[ControlView]](s"$endPoint/", timeout, List("ids" -> ids))
  }

  def audit(id: String, entity_type: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[EventControl]]] = {
    ws.get[List[EventControl]](s"$endPoint/$entity_type/$id/audit", timeout)
  }

  def create(user_id: String, timeout: Long = defaultTimeOut, control_creation: ControlCreation)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Control]] = {
    ws.put[Control](s"$endPoint/assign_to/$user_id", timeout, Json.toJson(control_creation))
  }

  def update(id: String, timeout: Long = defaultTimeOut, control_update: ControlUpdate)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Control]] = {
    ws.post[Control](s"$endPoint/$id", timeout, Json.toJson(control_update))
  }

  def updateBlock(
    id:                   String,
    block_id:             String,
    timeout:              Long               = defaultTimeOut,
    control_block_update: ControlBlockUpdate
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, Control]] = {
    ws.post[Control](s"$endPoint/$id/block/$block_id", timeout, Json.toJson(control_block_update))
  }

  def publish(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Control]] = {
    ws.post[Control](s"$endPoint/$id/publish", timeout, JsNull)
  }

  def search(
    criteria:       ControlViewSearchCriteria,
    table_criteria: TableSearch,
    timeout:        Long                      = defaultTimeOut
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[ControlView]]] = {
    val params_query = LangUtils.productToQueryString(criteria) ++ LangUtils.productToQueryString(table_criteria)
    ws.get[PaginatedSequence[ControlView]](s"$endPoint/search", timeout, params_query)
  }
}