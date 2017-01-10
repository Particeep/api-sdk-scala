package com.particeep.api

import com.particeep.api.core.{ResponseParser, WSClient}

trait FormCapability {
  self: WSClient =>

  val form: FormClient = new FormClient(this)
}

class FormClient(ws: WSClient) extends ResponseParser {

  //private val endPoint: String = "/form"
}
