package com.knoldus.api

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.api.broker.kafka.{KafkaProperties, PartitionKeyStrategy}
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}
import play.api.libs.json.{Format, Json}

object CarService  {
  val TOPIC_NAME = "purchase"
}


trait CarService extends Service {


  def carInfo(id: String): ServiceCall[NotUsed, String]


  def consumeCar(id: String): ServiceCall[NotUsed, CarTypeInfo]



  def greetingsTopic(): Topic[GreetingMessageChanged]

  override final def descriptor = {
    import Service._
    // @formatter:off
    named("car")
      .withCalls(
        pathCall("/api/car/:car", carInfo _),
        pathCall("/api/consume/car", consumeCar _)
      )
      .withTopics(
        topic(CarService.TOPIC_NAME, greetingsTopic)
          .addProperty(
            KafkaProperties.partitionKeyStrategy,
            PartitionKeyStrategy[GreetingMessageChanged](_.name)
          )
      )
      .withAutoAcl(true)
    // @formatter:on
  }
}

case class GreetingMessage(message: String)

object GreetingMessage {

  implicit val format: Format[GreetingMessage] = Json.format[GreetingMessage]
}
case class GreetingMessageChanged(name: String, message: String)

object GreetingMessageChanged {

  implicit val format: Format[GreetingMessageChanged] = Json.format[GreetingMessageChanged]
}
