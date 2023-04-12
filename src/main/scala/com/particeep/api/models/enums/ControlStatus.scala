package com.particeep.api.models.enums

sealed trait ControlStatus extends Product with Serializable with Enum

object ControlStatus extends EnumHelper[ControlStatus] {

  case object ONGOING extends ControlStatus { val name: String = "ONGOING" }
  case object VALIDATED extends ControlStatus { val name: String = "VALIDATED" }
  case object REJECTED extends ControlStatus { val name: String = "REJECTED" }
  case object SUCCEED extends ControlStatus { val name: String = "SUCCEED" }

  val values: Set[ControlStatus] = Set(ONGOING, VALIDATED, REJECTED, SUCCEED)
}
