package com.particeep.api.models.control

import com.particeep.api.models.enums.ControlStatus
import play.api.libs.json.{ Json, OFormat }

import java.time.OffsetDateTime

final case class ControlView(
    id:          String,
    created_at:  OffsetDateTime,
    updated_at:  OffsetDateTime,
    deleted_at:  Option[OffsetDateTime],
    created_by:  String,
    assigned_to: String,
    level:       Int,
    target_id:   String,
    target_type: String,
    blocks:      List[ControlBlock],
    status:      ControlStatus,
    comment:     Option[String]
)

object ControlView {
  implicit val control_view_format: OFormat[ControlView] = Json.format[ControlView]
}

final case class ControlViewSearchCriteria(
    created_after:  Option[OffsetDateTime],
    created_before: Option[OffsetDateTime],
    level:          Option[Int],
    target_id:      Option[String],
    target_type:    Option[String],
    status:         Option[ControlStatus],
    ids:            Option[String]
)