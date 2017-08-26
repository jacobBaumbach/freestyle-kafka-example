package freestyleKafkaExample

import interpreters.TargetI._
import producers.ProducerExample._
import record.ProducerRecordExample
import types._

import cats._
import cats.implicits._

import freestyle._
import freestyle.implicits._

@module trait Application {
  val record: ProducerRecordExample
  val produce: kafkaProducerProvider.Producer
}

object ProducerRun extends App {

  def program[F[_]](topic: String, k: String, v: Long)(implicit A: Application[F]) = {
      import A._
  
      for {
        producerRecord <- record.producerRecord(topic, k.toString, v)
        metaData <- produce.send(producerRecord)
        _ <- produce.flush()
      } yield metaData
  }

  val topic  = "test"
  val k      = "freestyle"
  val v: Long  = 0L
  val output = program[Application.Op](topic, k, v).interpret[Target]
}
