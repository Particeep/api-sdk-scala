package com.particeep.api.models.enums

sealed trait ControlBlockStatus extends Product with Serializable with Enum

object ControlBlockStatus extends EnumHelper[ControlBlockStatus] {

  case object ONGOING extends ControlBlockStatus { val name: String = "ONGOING" }
  case object VALIDATED extends ControlBlockStatus { val name: String = "VALIDATED" }
  case object REJECTED extends ControlBlockStatus { val name: String = "REJECTED" }

  val values: Set[ControlBlockStatus] = Set(ONGOING, VALIDATED, REJECTED)
}
