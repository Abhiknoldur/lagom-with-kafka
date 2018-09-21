package com.knoldus.api
import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}


trait externalCarService extends Service{

  override final def descriptor:Descriptor={
    import Service._

    named("external-service")
      .withCalls(
        pathCall("/Abhiknoldur/5fd4e2183fcbc02615368d1dc0d9591d/raw", getCar _)
      ).withAutoAcl(true)

  }
  def getCar(): ServiceCall[NotUsed,CarTypeInfo]

}
