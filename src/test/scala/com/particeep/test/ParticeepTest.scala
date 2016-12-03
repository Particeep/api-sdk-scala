package com.particeep.test

import com.particeep.api._
import org.scalatest._

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class ParticeepSpec extends FlatSpec with Matchers {

  val baseUrl = "http://test-api.particeep.com"
  val credential = ApiCredential(
    apiKey = "d6a53e1a-fc8e-4251-9dda-fabbce5f2a2c",
    apiSecret = "9bb3c122-0272-4bed-a632-19d5d52c7b5e"
  )
  val version = "1"

  "the api client" should "load info" in {

    val ws = new ApiClient(baseUrl, credential, version) with InfoClient
    val rez_f = ws.info()

    val rez = Await.result(rez_f, 10 seconds)
    println(">>> info : " + info)

    true should ===(true)
  }

}

