package com.particeep.api.models.fundraise.loan

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import com.particeep.api.models.user.User
import play.api.libs.json._

case class Repayment(
  capital:                        Int,
  capital_offline:                Option[Int] = None,
  interest:                       Int,
  interest_offline:               Option[Int] = None,
  taxes:                          Int,
  amount:                         Int,
  amount_offline:                 Option[Int] = None,
  capital_remains_to_pay:         Int,
  capital_remains_to_pay_offline: Option[Int] = None,
  fees:                           Int,
  fees_offline:                   Option[Int] = None,
  is_paid:                        Boolean
)

case class RepaymentWithDate(
  date:      ZonedDateTime,
  repayment: Repayment
)

object RepaymentWithDate {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  private[this] implicit val repayment_format = Json.format[Repayment]
  private[this] implicit val user_format = Json.format[User]
  implicit val format = Json.format[RepaymentWithDate]

  val repayment_with_date_and_user_format: Format[(RepaymentWithDate, User)] = new Format[(RepaymentWithDate, User)] {
    def reads(json: JsValue): JsResult[(RepaymentWithDate, User)] = JsSuccess((
      RepaymentWithDate(
        date = (json \ "date").as[ZonedDateTime],
        repayment = (json \ "repayment").as[Repayment]
      ),
      (json \ "user").as[User]
    ))
    def writes(repaymentWithDateAndUser: (RepaymentWithDate, User)): JsValue =
      Json.toJson(repaymentWithDateAndUser._1).as[JsObject] + ("user" -> Json.toJson(repaymentWithDateAndUser._2))
  }
}
