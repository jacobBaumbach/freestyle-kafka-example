package freestyleKafkaExample
package interpreters

import algebras._
import algebras.ConsumerExample._
import algebras.ProducerExample._
import AsyncImplicit._
import types._

import better.files.File

import cats.implicits._

import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.consumer.{ConsumerRecords, ConsumerRecord}

import scala.collection.JavaConverters._
import scala.util.Try

object TargetI {

  implicit val betterFilesHandler: BetterFiles.Handler[Target] = new BetterFiles.Handler[Target] {
    override def file(path: String): Target[File] =
      Try(File(path)).toEither

    override def lineIterator(file: File): Target[Iterator[String]] =
      Try(file.lineIterator).toEither

    override def appendLine(file: File, line: String): Target[File] =
      Try(file.appendLine(line)).toEither
  }

  implicit val testDataHandler: TestDataF.Handler[Target] = new TestDataF.Handler[Target] {
    override def apply(tuple: (String, Long)): Target[TestData] =
      Right(TestData(tuple._1, tuple._2))

    override def stringApply(str: String): Target[TestData] =
      Try(TestData.stringApply(str)).toEither

    override def toString(td: TestData): Target[String] =
      Right(td.toString)
    
    override def unapply(testData: TestData): Target[(String, Long)] =
      Right((testData.k, testData.v))

    override def add2(testData: TestData): Target[TestData] =
      Right(testData.add2)

    override def subtract2(testData: TestData): Target[TestData] =
      Right(testData.subtract2)

  }

  implicit val producerRecordHandler: ProducerRecordExample.Handler[Target] = new ProducerRecordExample.Handler[Target] {
    override def producerRecord(topic: String, key: String, value: Long): Target[ProducerRecord[String, Long]] =
      Right(new ProducerRecord(topic, key, value))
  }

  implicit val consumerRecordHandler: ConsumerRecordExample.Handler[Target] = new ConsumerRecordExample.Handler[Target] {
    override def getRecords(crs: ConsumerRecords[String, Long], topic: String): Target[Iterable[ConsumerRecord[String, Long]]] =
      Right(crs.records(topic).asScala)

    override def getKey(cr: ConsumerRecord[String, Long]): Target[String] =
      Right(cr.key)

    override def getValue(cr: ConsumerRecord[String, Long]): Target[Long] =
      Right(cr.value)
  }

  implicit val kafkaProducerHandler: kafkaProducerProvider.Producer.Handler[Target] =
    new kafkaProducerProvider.KafkaProducerHandler[Target]()

  implicit val kafkaConsumerHandler: kafkaConsumerProvider.KafkaConsumerHandler[Target] =
    new kafkaConsumerProvider.KafkaConsumerHandler[Target]()

}


