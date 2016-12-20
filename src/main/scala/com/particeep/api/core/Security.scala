package com.particeep.api.core

import java.time.format.DateTimeFormatter
import java.time.{ZoneOffset, ZonedDateTime}

import play.api.libs.ws._

import scala.concurrent.ExecutionContext

trait WithSecurtiy {

  protected def secure(req: WSRequest, apiCredential: ApiCredential, timeOut: Long)(implicit exec: ExecutionContext) = {
    val today = buildDateHeader()
    req
      .withRequestTimeout(timeOut)
      .withHeaders(
        ("DateTime", today),
        ("Authorization", buildAuthorizationHeader(today, apiCredential))
      )
  }

  private def buildDateHeader(): String = {
    val date = ZonedDateTime.now(ZoneOffset.UTC).withNano(0)
    val format = DateTimeFormatter.ISO_INSTANT
    format.format(date)
  }

  private def buildAuthorizationHeader(toSign: String, apiCredential: ApiCredential): String = {
    buildAuthorizationHeader(apiCredential.apiKey, apiCredential.apiSecret, toSign)
  }

  private def buildAuthorizationHeader(apiKey: String, apiSecret: String, dataToSign: String): String = {
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
