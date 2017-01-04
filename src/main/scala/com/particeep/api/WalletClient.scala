package com.particeep.api

import com.particeep.api.core.{ResponseParser, WSClient}

trait WalletCapability {
  self: WSClient =>

  val wallet: WalletClient = new WalletClient(this)
}

class WalletClient(ws: WSClient) extends ResponseParser {

  //private val endPoint: String = "/wallet"
}
