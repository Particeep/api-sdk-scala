package com.particeep.api

import com.particeep.api.core.{ ApiCredential, ResponseParser, WSClient }
import com.particeep.api.models.{ ErrorResult, PaginatedSequence, TableSearch }
import com.particeep.api.models.fundraise.{ FundraiseData, FundraiseSearch, NbProjectsByCategory }
import com.particeep.api.utils.LangUtils

import scala.concurrent.{ ExecutionContext, Future }

trait FundraiseSearchCapability {
  self: WSClient =>

  val fundraise_search: FundraiseSearchClient = new FundraiseSearchClient(this)
}

class FundraiseSearchClient(ws: WSClient) extends ResponseParser {

  private[this] val endPoint: String = "/fundraises"
  implicit val format = FundraiseData.format
  implicit val project_by_category_format = NbProjectsByCategory.format

  def search(criteria: FundraiseSearch, table_criteria: TableSearch, timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, PaginatedSequence[FundraiseData]]] = {
    ws.url(s"$endPoint/search", timeout)
      .withQueryString(LangUtils.productToQueryString(criteria): _*)
      .withQueryString(LangUtils.productToQueryString(table_criteria): _*)
      .get
      .map(parse[PaginatedSequence[FundraiseData]])
  }

  def byIds(ids: List[String], timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, List[FundraiseData]]] = {
    ws.url(s"$endPoint/byIds", timeout)
      .withQueryString("ids" -> ids.mkString(","))
      .get()
      .map(parse[List[FundraiseData]])
  }

  def nbProjectsByActivityDomain(categories: List[String], timeout: Long = -1)(implicit exec: ExecutionContext, credentials: ApiCredential): Future[Either[ErrorResult, List[NbProjectsByCategory]]] = {
    ws.url(s"$endPoint/info/categories", timeout)
      .withQueryString("categories" -> categories.mkString(","))
      .get()
      .map(parse[List[NbProjectsByCategory]])
  }
}
