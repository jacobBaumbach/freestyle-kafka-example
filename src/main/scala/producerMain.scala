package freestyleKafkaExample

import interpreters.TargetI._
import modules.ProducerModule
import algebras.TestData
import types._

import cats._
import cats.implicits._

import freestyle._
import freestyle.implicits._

object ProducerRun extends App {

  def program[F[_]](topic: String, td: TestData)(implicit PM: ProducerModule[F]) = {
      import PM._
  
    for {
        tdA2 <- testData.add2(td)
        kv <- testData.unapply(tdA2)
        producerRecord <- record.producerRecord(topic, kv._1, kv._2)
        metaData <- produce.send(producerRecord)
        _ <- produce.flush()
      } yield metaData
  }

  val topic  = "test"
  val td      = TestData("freestyle", 0L)
  val output = program[ProducerModule.Op](topic, td).interpret[Target]
}
