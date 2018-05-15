package com.particeep.api

import com.particeep.api.core._
import com.particeep.api.models.{ ErrorResult, PaginatedSequence, TableSearch }
import com.particeep.api.utils.LangUtils
import play.api.libs.json.Json

import scala.concurrent.{ ExecutionContext, Future }
import com.particeep.api.models.kpi._

trait KpiCapability {
  self: WSClient =>

  val kpi: KpiClient = new KpiClient(this)
  def kpi(credentials: ApiCredential): KpiClient = new KpiClient(this, Some(credentials))
}

object KpiClient {
  private val endPoint: String = "/kpi"
  private implicit val kpi_format = Kpi.format
  private implicit val kpi_value_format = KpiValue.format
  private implicit val kpi_option_format = KpiCreation.format
  private implicit val edition_format = KpiValueCreation.format
  private implicit val kpi_value_update_format = KpiValueUpdate.format
  private implicit val kpi_update_format = KpiUpdate.format

  case class DeleteKpiValues(ids: String)
}

class KpiClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends WithWS with WithCredentials with EntityClient {

  import KpiClient._

  def byId(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Kpi]] = {
    ws.get[Kpi](s"$endPoint/$id", timeout)
  }

  def create(kpi_creation: KpiCreation, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Kpi]] = {
    ws.put[Kpi](s"$endPoint", timeout, Json.toJson(kpi_creation))
  }

  def addValues(kpi_id: String, kpi_values: KpiValueCreation, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Kpi]] = {
    ws.put[Kpi](s"$endPoint/$kpi_id/values", timeout, Json.toJson(kpi_values))
  }

  def updateValues(kpi_value_update: KpiValueUpdate, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, KpiValue]] = {
    ws.post[KpiValue](s"$endPoint/values", timeout, Json.toJson(kpi_value_update))
  }

  def deleteValues(kpi_id: String, ids_value: Seq[String], timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Kpi]] = {
    val valueToDelete = DeleteKpiValues(ids_value.mkString(","))
    ws.delete[Kpi](s"$endPoint/$kpi_id/values", timeout, Json.toJson(""), LangUtils.productToQueryString(valueToDelete))
  }

  def search(criteria: KpiSearch, table_criteria: TableSearch, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[Kpi]]] = {
    ws.get[PaginatedSequence[Kpi]](s"$endPoint/search", timeout, LangUtils.productToQueryString(criteria) ++ LangUtils.productToQueryString(table_criteria))
  }

  def delete(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Kpi]] = {
    ws.delete[Kpi](s"$endPoint/$id", timeout)
  }

  def update(id: String, kpi_edition: KpiUpdate, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Kpi]] = {
    ws.post[Kpi](s"$endPoint/$id", timeout, Json.toJson(kpi_edition))
  }
}
