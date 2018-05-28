package com.particeep.api

import com.particeep.api.core._
import com.particeep.api.models.{ ErrorResult, PaginatedSequence, TableSearch }
import com.particeep.api.models.financial_product._
import com.particeep.api.utils.LangUtils
import play.api.libs.json.Json

import scala.concurrent.{ ExecutionContext, Future }

trait FinancialProductCapability {
  self: WSClient =>

  val financial_product: FinancialProductClient = new FinancialProductClient(this)

  def financial_product(credentials: ApiCredential): FinancialProductClient = new FinancialProductClient(this, Some(credentials))
}

object FinancialProductClient {
  private val endPoint: String = "/financial-product"
  private implicit val format = FinancialProduct.format
  private implicit val creation_format = FinancialProductCreation.format
  private implicit val edition_format = FinancialProductEdition.format
  private implicit val composition_creation_format = FinancialProductCompositionCreation.format
}

class FinancialProductClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends WithWS with WithCredentials with EntityClient {

  import FinancialProductClient._

  def byId(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FinancialProduct]] = {
    ws.get[FinancialProduct](s"$endPoint/$id", timeout)
  }

  def create(financial_product_creation: FinancialProductCreation, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FinancialProduct]] = {
    ws.put[FinancialProduct](s"$endPoint", timeout, Json.toJson(financial_product_creation))
  }

  def update(id: String, financial_product_edition: FinancialProductEdition, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FinancialProduct]] = {
    ws.post[FinancialProduct](s"$endPoint/$id", timeout, Json.toJson(financial_product_edition))
  }

  def search(criteria: FinancialProductSearch, table_criteria: TableSearch, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[FinancialProduct]]] = {
    ws.get[PaginatedSequence[FinancialProduct]](s"$endPoint/search", timeout, LangUtils.productToQueryString(criteria) ++ LangUtils.productToQueryString(table_criteria))
  }

  def delete(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, FinancialProduct]] = {
    ws.delete[FinancialProduct](s"$endPoint/$id", timeout)
  }

  def getComposition(id: String, timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[FinancialProduct]]] = {
    ws.get[List[FinancialProduct]](s"$endPoint/$id/composition", timeout)
  }

  def addToComposition(
    id:                                      String,
    financial_product_composition_creations: List[FinancialProductCompositionCreation],
    timeout:                                 Long                                      = defaultTimeOut
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[FinancialProduct]]] = {
    ws.post[List[FinancialProduct]](s"$endPoint/$id/composition", timeout, Json.toJson(financial_product_composition_creations))
  }

  def removeFromComposition(id: String, ids: Seq[String], timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[FinancialProduct]]] = {
    ws.delete[List[FinancialProduct]](s"$endPoint/$id/composition", timeout, Json.toJson(""), List("ids" -> ids.mkString(",")))
  }

}
