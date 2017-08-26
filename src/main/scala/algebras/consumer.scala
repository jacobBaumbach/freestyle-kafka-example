package freestyleKafkaExample
package algebras

import types._

import collection.JavaConversions._

import freestyle.kafka.UnderlyingKafkaConsumer
import freestyle.kafka.consumer._

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.{StringDeserializer, LongDeserializer}

object ConsumerExample {
  val configs: java.util.Map[String, Object] =
    mapAsJavaMap(Map(
      "bootstrap.servers" -> "localhost:9092",
      "group.id" -> "freestyle-kafka-consumer-example"
    ))

  val kafkaConsumer: KafkaConsumer[String, Long] = new KafkaConsumer(configs, new StringDeserializer(), new LongDeserializer())
  val kafkaConsumerProvider = new KafkaConsumerProvider[String, Long]()
  implicit val underlyingKafkaConsumer =
    new UnderlyingKafkaConsumer[String, Long]{
      override def consumer: KafkaConsumer[String, Long] = kafkaConsumer
    }

}
