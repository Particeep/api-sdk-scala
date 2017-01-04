package com.particeep.api.models.wallet

import java.time.ZonedDateTime

import com.particeep.api.core.Formatter
import com.particeep.api.models.Address
import com.particeep.api.models.enums.WalletType.WalletType
import play.api.libs.json.Json

case class WalletCreation(
  owner_id:           String,
  owner_type:         String,
  wallet_type:        WalletType,
  address:            Address,
  owner_ip:           String,
  email:              String,
  first_name:         String,
  last_name:          String,
  gender:             String,
  birthday:           ZonedDateTime,
  nationality:        Option[String],
  countryOfResidence: Option[String],
  businessName:       Option[String]
)

object WalletCreation {
  implicit val date_format = Formatter.ZonedDateTimeWrites
  val format = Json.format[WalletCreation]
}
