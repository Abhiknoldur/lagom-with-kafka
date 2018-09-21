package com.knoldus.api

import javax.inject.{Inject, Named}
import akka.actor.{ActorRef, ActorSystem}
import akka.stream.Materializer

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

class PollingScheduler @Inject()(session: CassandraSession, system: ActorSystem, registry: PersistentEntityRegistry)(implicit val mat: Materializer, ec: ExecutionContext) {

  system.scheduler.schedule(
    initialDelay = 0.microseconds,
    interval = 300.seconds){
    println("hey hey")
  }

}


