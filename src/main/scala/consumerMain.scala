package freestyleKafkaExample

import algebras.TestData
import interpreters.TargetI._
import modules.ConsumerModule
import types._

import cats._
import cats.implicits._

import freestyle._
import freestyle.implicits._

import scala.concurrent.duration._

import org.apache.kafka.clients.consumer.ConsumerRecord

object ConsumerRun extends App {

  def program[F[_]](topic: String)(implicit CM: ConsumerModule[F]) = {
    import CM._

    for {
      _ <- consume.subscribe(topic :: Nil)
      _ <- consume.commitSync()
      consumerRecords <- consume.poll(5.seconds)
      list <- record.getRecords(consumerRecords, topic)
      listTDS2 = list.map(record.getKeyValue)
                     .map(_.flatMap(testData.apply))
                     .map(_.flatMap(testData.subtract2))
    } yield listTDS2
  }

  val topic = "test"
  val output = program[ConsumerModule.Op](topic).interpret[Target]
}
