package com.knoldus.impl

import com.knoldus.api
import com.knoldus.api.{CarService}
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.broker.TopicProducer
import com.lightbend.lagom.scaladsl.persistence.{EventStreamElement, PersistentEntityRegistry}

class CarServiceImpl(persistentEntityRegistry: PersistentEntityRegistry) extends CarService {

  override def hello(id: String) = ServiceCall { _ =>
    val ref = persistentEntityRegistry.refFor[CarEntity](id)

    ref.ask(Hello(id))
  }

  override def useGreeting(id: String) = ServiceCall { request =>

    val ref = persistentEntityRegistry.refFor[CarEntity](id)

    ref.ask(UseGreetingMessage(request.message))
  }


  override def greetingsTopic(): Topic[api.GreetingMessageChanged] =
    TopicProducer.singleStreamWithOffset {
      fromOffset =>
        persistentEntityRegistry.eventStream(CarEvent.Tag, fromOffset)
          .map(ev => (convertEvent(ev), ev.offset))
    }

  private def convertEvent(helloEvent: EventStreamElement[CarEvent]): api.GreetingMessageChanged = {
    helloEvent.event match {
      case GreetingMessageChanged(msg) => api.GreetingMessageChanged(helloEvent.entityId, msg)
    }
  }
}
