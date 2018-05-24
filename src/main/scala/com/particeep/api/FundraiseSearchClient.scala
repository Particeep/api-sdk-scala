package com.particeep.api

import com.particeep.api.core._
import com.particeep.api.models.{ ErrorResult, PaginatedSequence, TableSearch }
import com.particeep.api.models.fundraise.{ FundraiseData, FundraiseSearch, NbProjectsByCategory }
import com.particeep.api.utils.LangUtils

import scala.concurrent.{ ExecutionContext, Future }

trait FundraiseSearchCapability {
  self: WSClient =>

  val fundraise_search: FundraiseSearchClient = new FundraiseSearchClient(this)
  def fundraise_search(credentials: ApiCredential): FundraiseSearchClient = new FundraiseSearchClient(this, Some(credentials))
}

object FundraiseSearchClient {
  private val endPoint: String = "/fundraises"
  private implicit val format = FundraiseData.format
  private implicit val project_by_category_format = NbProjectsByCategory.format
}

class FundraiseSearchClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends WithWS with WithCredentials with EntityClient {

  import FundraiseSearchClient._

  def search(
    criteria:       FundraiseSearch,
    table_criteria: TableSearch,
    timeout:        Long            = defaultTimeOut
  )(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[FundraiseData]]] = {
    ws.get[PaginatedSequence[FundraiseData]](s"$endPoint/search", timeout, LangUtils.productToQueryString(criteria) ++ LangUtils.productToQueryString(table_criteria))
  }

  def byIds(ids: List[String], timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[FundraiseData]]] = {
    ws.get[List[FundraiseData]](s"$endPoint/byIds", timeout, List("ids" -> ids.mkString(",")))
  }

  def nbProjectsByActivityDomain(categories: List[String], timeout: Long = defaultTimeOut)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[NbProjectsByCategory]]] = {
    ws.get[List[NbProjectsByCategory]](s"$endPoint/info/categories", timeout, List("categories" -> categories.mkString(",")))
  }
}
