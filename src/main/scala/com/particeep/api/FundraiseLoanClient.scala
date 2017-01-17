package com.particeep.api

import com.particeep.api.core.{ResponseParser, WSClient}

trait FundraiseLoanCapability {
  self: WSClient =>

  val fundraise_loan: FundraiseLoanClient = new FundraiseLoanClient(this)
}

class FundraiseLoanClient(ws: WSClient) extends ResponseParser {

}
