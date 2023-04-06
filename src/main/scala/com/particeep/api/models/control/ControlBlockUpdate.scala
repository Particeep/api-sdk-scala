package com.particeep.api.models.control

import com.particeep.api.models.enums.ControlBlockStatus
import play.api.libs.json.{ Json, OFormat }

final case class ControlBlockUpdate(
    doc_ids: Seq[String],
    status:  Option[ControlBlockStatus],
    comment: Option[String]
)

object ControlBlockUpdate {
  implicit val control_block_update_format: OFormat[ControlBlockUpdate] = Json.format[ControlBlockUpdate]
}