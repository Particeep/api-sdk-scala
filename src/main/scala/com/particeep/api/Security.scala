package com.particeep.api

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
    val date = ZonedDateTime.now(ZoneOffset.UTC)
    val format = DateTimeFormatter.ISO_DATE_TIME
    format.format(date)
  }

  private def buildAuthorizationHeader(toSign: String, apiCredential: ApiCredential): String = {
    buildAuthorizationHeader(toSign, apiCredential.apiKey, apiCredential.apiSecret)
  }

  private def buildAuthorizationHeader(apiKey: String, apiSecret: String, dateTime: String): String = {
    val toSign: String = apiSecret + apiKey + dateTime

    val messageBytes = toSign.getBytes("UTF-8")
    val secretBytes = apiSecret.getBytes("UTF-8")

    val result = Crypto.sign(messageBytes, secretBytes)

    val hexChars = Crypto.encodeToHex(result)

    val sign = new String(hexChars).toLowerCase()
    val signature = Crypto.encodeBase64(sign)

    s"PTP:$apiKey:$signature"
  }

}
