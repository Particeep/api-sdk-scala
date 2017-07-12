package com.particeep.test

import scala.language.postfixOps
import com.particeep.api.models.user.User
import com.particeep.api.core.ApiClient
import com.particeep.api.models.ErrorResult
import com.particeep.api.UserCapability
import com.particeep.api.ParticeepApi
import org.scalatest._

import scala.concurrent.{ Await, Future }
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class UserTest extends FlatSpec with Matchers {

  "the api client" should "load user by id with correct date format" in {

    val user_id = "bf5788e8-9756-4d18-8b0f-100d7fba17a2"
    val ws = new ApiClient(ConfigTest.baseUrl, ConfigTest.version, Some(ConfigTest.credential)) with UserCapability
    val rez_f: Future[Either[ErrorResult, User]] = ws.user.byId(user_id)

    val rez = Await.result(rez_f, 10 seconds)
    rez.isRight shouldBe true

    val user = rez.right.get
    user.id shouldBe user_id

    val created_at = user.created_at.map(_.toString).getOrElse("")
    created_at shouldBe "2015-08-10T00:00Z"
  }

  "the api client" should "load user by id with direct credentials" in {

    val user_id = "bf5788e8-9756-4d18-8b0f-100d7fba17a2"
    val ws = new ApiClient(ConfigTest.baseUrl, ConfigTest.version, Some(ConfigTest.credential)) with UserCapability
    val rez_f: Future[Either[ErrorResult, User]] = ws.user.byId(user_id)

    val rez = Await.result(rez_f, 10 seconds)
    rez.isRight shouldBe true

    val user = rez.right.get
    user.id shouldBe user_id
  }

  "the api client" should "load user by id with custom credentials" in {

    val user_id = "bf5788e8-9756-4d18-8b0f-100d7fba17a2"
    val ws = new ApiClient(ConfigTest.baseUrl, ConfigTest.version) with UserCapability
    val credentials = ConfigTest.credential
    val rez_f: Future[Either[ErrorResult, User]] = ws.user(credentials).byId(user_id)

    val rez = Await.result(rez_f, 10 seconds)
    rez.isRight shouldBe true

    val user = rez.right.get
    user.id shouldBe user_id
  }

  "the api client" should "load user by id with helper" in {

    val user_id = "bf5788e8-9756-4d18-8b0f-100d7fba17a2"
    val ws = ParticeepApi.test(ConfigTest.credential.apiKey, ConfigTest.credential.apiSecret)
    val rez_f: Future[Either[ErrorResult, User]] = ws.user.byId(user_id)

    val rez = Await.result(rez_f, 10 seconds)
    rez.isRight shouldBe true

    val user = rez.right.get
    user.id shouldBe user_id
  }

  "the api client" should "load user by id with helper and overload credentials" in {

    val user_id = "bf5788e8-9756-4d18-8b0f-100d7fba17a2"
    val ws = ParticeepApi.test(ConfigTest.credential.apiKey, ConfigTest.credential.apiSecret)
    val credentials = ConfigTest.credential

    val rez_f: Future[Either[ErrorResult, User]] = ws.user(credentials).byId(user_id)

    val rez = Await.result(rez_f, 10 seconds)
    rez.isRight shouldBe true

    val user = rez.right.get
    user.id shouldBe user_id
  }
}
