package com.particeep.test

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import org.scalatest._

trait BaseTest extends FlatSpec with Matchers {

  implicit val system: ActorSystem = ActorSystem("test-system")
  implicit val mat: akka.stream.Materializer = ActorMaterializer()
}
