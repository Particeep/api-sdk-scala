package com.particeep.api.models.enums

sealed trait ControlControlBlockType extends ControlBlockType

object ControlControlBlockType extends EnumHelper[ControlControlBlockType] {
  case object COMMENT extends ControlControlBlockType { val name: String = "COMMENT" }

  override def values: Set[ControlControlBlockType] = Set(COMMENT)
}
