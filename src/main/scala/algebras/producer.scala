package freestyleKafkaExample
package algebras

import types._

import cats._
import cats.implicits._

import freestyle.kafka.{KafkaProducerConfig, UnderlyingKafkaProducer}
import freestyle.kafka.producer.KafkaProducerProvider

import org.apache.kafka.common.serialization.{StringSerializer, LongSerializer}

import org.apache.kafka.clients.producer.{ProducerRecord, KafkaProducer}

object ProducerExample {
  val configs: Map[String, Any] =
    Map(
      "bootstrap.servers" -> "localhost:9092",
      "client.id" -> "freestyle-kafka-producer-example"
    )

  val keyValueSerializers = Some((new StringSerializer(), new LongSerializer()))
  val kafkaProducerConfig: KafkaProducerConfig[String, Long] = KafkaProducerConfig(configs, keyValueSerializers)
  val kafkaProducer: KafkaProducer[String, Long] = kafkaProducerConfig.producer
  implicit val underlyingKafkaProducer =
    new UnderlyingKafkaProducer[String,Long]{
      override def producer: KafkaProducer[String, Long] = kafkaProducer
    }

  val kafkaProducerProvider = new KafkaProducerProvider[String, Long]()
}
