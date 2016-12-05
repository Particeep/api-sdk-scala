package com.particeep.test

import scala.language.postfixOps
import com.particeep.api._
import com.particeep.api.core.ApiClient
import org.scalatest._
import play.api.libs.json.JsError

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class ParticeepSpec extends FlatSpec with Matchers {

  "the api client" should "load info" in {

    val ws = new ApiClient(ConfigTest.baseUrl, ConfigTest.credential, ConfigTest.version) with InfoClient
    val rez_f: Future[Either[JsError, Info]] = ws.info()

    val rez = Await.result(rez_f, 10 seconds)
    rez.isRight shouldBe true

    val info = rez.right.get
    info.version shouldBe "1"
  }
}
