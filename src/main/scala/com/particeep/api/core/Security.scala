package com.particeep.api.core

import java.time.format.DateTimeFormatter
import java.time.{ ZoneOffset, ZonedDateTime }

import com.ning.http.client.AsyncHttpClient
import play.api.libs.ws._

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

trait WithSecurtiy {

  protected def secure(req: StandaloneWSRequest, apiCredential: ApiCredential, timeOut: Long)(implicit exec: ExecutionContext) = {
    val timeout_ms = if (timeOut < 0) Int.MaxValue else timeOut
    val today = buildDateHeader()
    req
      .withRequestTimeout(timeout_ms.milliseconds)
      .withHttpHeaders(
        ("DateTime", today),
        ("Authorization", buildAuthorizationHeader(today, apiCredential))
      )
  }

  protected def secure(req: AsyncHttpClient#BoundRequestBuilder, apiCredential: ApiCredential, timeOut: Long)(implicit exec: ExecutionContext) = {
    val timeout_ms = if (timeOut < 0) Int.MaxValue else timeOut
    val today = buildDateHeader()
    req
      .setRequestTimeout(timeout_ms.toInt)
      .addHeader("DateTime", today)
      .addHeader("Authorization", buildAuthorizationHeader(today, apiCredential))
  }

  private[this] def buildDateHeader(): String = {
    val date = ZonedDateTime.now(ZoneOffset.UTC).withNano(0)
    val format = DateTimeFormatter.ISO_INSTANT
    format.format(date)
  }

  private[this] def buildAuthorizationHeader(toSign: String, apiCredential: ApiCredential): String = {
    buildAuthorizationHeader(apiCredential.apiKey, apiCredential.apiSecret, toSign)
  }

  private[this] def buildAuthorizationHeader(apiKey: String, apiSecret: String, dataToSign: String): String = {
    val toSign: String = apiSecret + apiKey + dataToSign

    val messageBytes = toSign.getBytes("UTF-8")
    val secretBytes = apiSecret.getBytes("UTF-8")

    val result = Crypto.sign(messageBytes, secretBytes)

    val hexChars = Crypto.encodeToHex(result)

    val sign = new String(hexChars).toLowerCase()
    val signature = Crypto.encodeBase64(sign)

    s"PTP:$apiKey:$signature"
  }
}
