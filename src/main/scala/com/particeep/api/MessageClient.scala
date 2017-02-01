package com.particeep.api

import com.particeep.api.core.{ResponseParser, WSClient}
import com.particeep.api.models.{ErrorResult, PaginatedSequence}
import com.particeep.api.models.message.{Conversation, Message, MessageSearch}
import com.particeep.api.utils.LangUtils

import scala.concurrent.{ExecutionContext, Future}

trait MessageCapability {
  self: WSClient =>

  val message: MessageClient = new MessageClient(this)
}

class MessageClient(ws: WSClient) extends ResponseParser {

  private val endPoint: String = "/messages"

  def byId(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Message]] = {
    ws.url(s"$endPoint/$id", timeout).get().map(parse[Message])
  }

  def delete(id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Message]] = {
    ws.url(s"$endPoint/$id", timeout).delete().map(parse[Message])
  }

  def allConversations(user_id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, Set[Conversation]]] = {
    ws.url(s"$endPoint/conversations/$user_id", timeout).get().map(parse[Set[Conversation]])
  }

  def conversation(first_user_id: String, second_user_id: String, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, List[Message]]] = {
    ws.url(s"$endPoint/conversations/$first_user_id/$second_user_id", timeout).get().map(parse[List[Message]])
  }

  def search(criteria: MessageSearch, timeout: Long = -1)(implicit exec: ExecutionContext): Future[Either[ErrorResult, PaginatedSequence[Message]]] = {
    ws.url(s"$endPoint/search", timeout)
      .withQueryString(LangUtils.productToQueryString(criteria): _*)
      .get
      .map(parse[PaginatedSequence[Message]])
  }


}
