package com.particeep.api.models.enterprise

import play.api.libs.json.Json

/**
 * Created by Noe on 10/03/2017.
 */
case class NbEnterprisesByActivityDomain(
    activity_domain: Option[String] = None,
    nb_enterprises:  Int            = 0
)

object NbEnterprisesByActivityDomain {
  val format = Json.format[NbEnterprisesByActivityDomain]
}
