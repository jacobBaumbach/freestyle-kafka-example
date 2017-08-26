package freestyleKafkaExample
package algebras

import types._

import cats.implicits._

import freestyle._

import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.consumer.{ConsumerRecords, ConsumerRecord}

@free trait ProducerRecordExample {
  def producerRecord(topic: String, key: String, value: Long): FS[ProducerRecord[String, Long]]
}

@free trait ConsumerRecordExample {
  def getRecords(crs: ConsumerRecords[String, Long], topic: String): FS[Iterable[ConsumerRecord[String, Long]]]
  def getKey(cr: ConsumerRecord[String, Long]): FS[String]
  def getValue(cr: ConsumerRecord[String, Long]): FS[Long]
  def getKeyValue(cr: ConsumerRecord[String, Long]): FS[(String, Long)] =
        (getKey(cr) |@| getValue(cr)).tupled      
}
