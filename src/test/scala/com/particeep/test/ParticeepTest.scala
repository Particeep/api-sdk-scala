package com.particeep.test

import java.time.{ZoneOffset, ZonedDateTime}

import scala.language.postfixOps
import com.particeep.api._
import com.particeep.api.core.ApiClient
import com.particeep.api.models.ErrorResult
import org.scalatest._
import play.api.libs.json.Json

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class ParticeepTest extends FlatSpec with Matchers {

  "the api client" should "load info" in {

    val ws = new ApiClient(ConfigTest.baseUrl, ConfigTest.credential, ConfigTest.version) with InfoCapability

    val rez_f: Future[Either[ErrorResult, Info]] = ws.info.info()

    val rez = Await.result(rez_f, 10 seconds)
    rez.isRight shouldBe true

    val info = rez.right.get
    info.version shouldBe "1"
  }

  "the api client" should "format date in ISO 8601" in {

    val date = ZonedDateTime.now(ZoneOffset.UTC)
      .withYear(1980)
      .withMonth(1)
      .withDayOfMonth(2)
      .withHour(3)
      .withMinute(20)
      .withSecond(12)
      .withNano(0)

    val user = User(id = "1234", birthday = Some(date))
    val json = Json.toJson(user)

    val result = Json.parse("""
        |{
        | "id": "1234",
        | "birthday": "1980-01-02T03:20:12Z"
        |}
        """.stripMargin)

    Json.prettyPrint(json) shouldBe Json.prettyPrint(result)
  }
}
