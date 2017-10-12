package com.particeep.api.core

import java.time.{ ZoneOffset, ZonedDateTime }
import java.time.format.DateTimeFormatter

import play.api.libs.json.Writes

object Formatter {
  implicit val ZonedDateTimeWrites = Writes.temporalWrites[ZonedDateTime, DateTimeFormatter](DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneOffset.UTC))
}
