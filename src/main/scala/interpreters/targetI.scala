package freestyleKafkaExample
package interpreters

import AsyncImplicit._
import types._

import freestyleKafkaExample.consumers.ConsumerExample._
import freestyleKafkaExample.producers.ProducerExample._
import freestyleKafkaExample.record.ProducerRecordExample

import cats.implicits._

import org.apache.kafka.clients.producer.ProducerRecord

object TargetI {

  implicit val recordHandler: ProducerRecordExample.Handler[Target] = new ProducerRecordExample.Handler[Target] {
    override def producerRecord(topic: String, key: String, value: Long): Target[ProducerRecord[String, Long]] =
      Right(new ProducerRecord(topic, key, value))
  }
  
  implicit val kafkaProducerHandler: kafkaProducerProvider.Producer.Handler[Target] =
    new kafkaProducerProvider.KafkaProducerHandler[Target]()

  implicit val kafkaConsumerHandler: kafkaConsumerProvider.KafkaConsumerHandler[Target] =
    new kafkaConsumerProvider.KafkaConsumerHandler[Target]()
}


