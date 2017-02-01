package com.particeep.api.models.message

case class MessageCreation(
                            title: Option[String] = None,
                            body: Option[String] = None,
                            link: Option[String] = None,
                            author_id: String,
                            target_id: String,
                            target_type: String
                          )
