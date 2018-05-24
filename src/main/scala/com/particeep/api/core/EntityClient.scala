package com.particeep.api.core

import org.slf4j.LoggerFactory

trait WithCredentials {
  def credentials: Option[ApiCredential]
}

trait WithWS {
  def ws: WSClient
  val defaultTimeOut: Long = ws.defaultTimeOut
  val defaultImportTimeOut: Long = ws.defaultImportTimeOut
}

trait EntityClient {
  self: WithWS with WithCredentials =>

  private[this] final lazy val log = LoggerFactory.getLogger(this.getClass)

  protected implicit lazy val creds: ApiCredential =
    this.credentials
      .orElse(this.ws.credentials)
      .getOrElse {
        log.warn("""
        You haven't set any api credentials. Your call to Particeep's API won't work.
        Please set your api_key and api_secret to to continue.

        More information in the documentation : https://github.com/Particeep/api-sdk-scala
        """)
        ApiCredential("", "")
      }
}
