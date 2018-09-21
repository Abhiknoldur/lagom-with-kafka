package com.knoldus.api

import play.api.libs.json.{Format, Json}


case class CarTypeInfo(
                    car: String,
                    manufacturer: String,
                    model: String,
                    price: Double,
                    wiki: String
                  )

object CarTypeInfo{
  implicit val format: Format[CarTypeInfo] = Json.format[CarTypeInfo]
}