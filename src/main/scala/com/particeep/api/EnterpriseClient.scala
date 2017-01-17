package com.particeep.api

import com.particeep.api.core.{ResponseParser, WSClient}

trait EnterpriseCapability {
  self: WSClient =>

  val enterprise: EnterpriseClient = new EnterpriseClient(this)
}

class EnterpriseClient(ws: WSClient) extends ResponseParser {

  //private val endPoint: String = "/enterprise"
}
