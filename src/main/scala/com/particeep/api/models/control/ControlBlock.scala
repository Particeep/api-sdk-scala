package com.particeep.api.models.control

import com.particeep.api.models.enums.{ ControlBlockStatus, ControlBlockType }
import play.api.libs.json.{ Json, OFormat }

final case class ControlBlock(
    id:         String,
    block_type: ControlBlockType,
    path:       String,
    status:     ControlBlockStatus,
    target_id:  Option[String],
    comment:    Option[String],
    doc_ids:    Seq[String]
)

object ControlBlock {
  implicit val control_block_format: OFormat[ControlBlock] = Json.format[ControlBlock]
}