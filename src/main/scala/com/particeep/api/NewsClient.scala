package com.particeep.api

import com.particeep.api.core._
import com.particeep.api.models.{ ErrorResult, PaginatedSequence }
import com.particeep.api.models.news._
import com.particeep.api.utils.LangUtils
import play.api.libs.json.Json

import scala.concurrent.{ ExecutionContext, Future }

trait NewsCapability {
  self: WSClient =>

  val news: NewsClient = new NewsClient(this)
  def news(credentials: ApiCredential): NewsClient = new NewsClient(this, Some(credentials))
}

object NewsClient {

  private val endPoint: String = "/newsfeed"

  private implicit val format = News.format
  private implicit val prev_and_next_format = NewsPrevAndNext.format
  private implicit val creation_format = NewsCreation.format
  private implicit val edition_format = NewsEdition.format
}

class NewsClient(val ws: WSClient, val credentials: Option[ApiCredential] = None) extends ResponseParser with WithWS with WithCredentials with EntityClient {

  import NewsClient._

  def byIds(ids: Seq[String], timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[News]]] = {
    ws.url(s"$endPoint", timeout)
      .addQueryStringParameters("ids" -> ids.mkString(","))
      .get()
      .map(parse[List[News]])
  }

  def getPrevAndNext(id: String, limit: Int, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, NewsPrevAndNext]] = {
    ws.url(s"$endPoint/with-prev-next/$id/$limit").get().map(parse[NewsPrevAndNext])
  }

  def delete(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, News]] = {
    ws.url(s"$endPoint/$id", timeout).delete().map(parse[News])
  }

  def add(news_creation: NewsCreation, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, News]] = {
    ws.url(s"$endPoint").put(Json.toJson(news_creation)).map(parse[News])
  }

  def edit(id: String, news_edition: NewsEdition, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, News]] = {
    ws.url(s"$endPoint/$id").post(Json.toJson(news_edition)).map(parse[News])
  }

  def search(criteria: NewsSearch, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[News]]] = {
    ws.url(s"$endPoint/search", timeout)
      .addQueryStringParameters(LangUtils.productToQueryString(criteria): _*)
      .get
      .map(parse[PaginatedSequence[News]])
  }
}
