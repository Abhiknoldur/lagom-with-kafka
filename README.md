# Car

 Write a lagom microservice that connects to an external service and puts the data into Kafka via scheduler.

First, connect to an external service and then ingest the data into Kafka, this is to be done via Akka scheduler which polls external service every 5 mins(Make it configurable via environment variable). Once the data is ingested into Kafka, write a consumer or subscriber which subscribes the same data.


