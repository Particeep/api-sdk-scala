package com.particeep.test

import scala.language.postfixOps
import com.particeep.api.models.user.User
import com.particeep.api.core.ApiClient
import com.particeep.api.models.ErrorResult
import com.particeep.api.UserCapability
import org.scalatest._

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class UserTest extends FlatSpec with Matchers {

  "the api client" should "load user by id" in {

    val user_id = "bf5788e8-9756-4d18-8b0f-100d7fba17a2"
    val ws = new ApiClient(ConfigTest.baseUrl, ConfigTest.credential, ConfigTest.version) with UserCapability
    val rez_f: Future[Either[ErrorResult, User]] = ws.user.byId(user_id)

    val rez = Await.result(rez_f, 10 seconds)
    rez.isRight shouldBe true

    val user = rez.right.get
    user.id shouldBe user_id
  }
}
