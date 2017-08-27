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

  def program[F[_]](topic: String, path: String)(implicit CM: ConsumerModule[F]) = {
    import CM.bftd._, CM._

    for {
      file <- bf.file(path)
      _ <- consume.subscribe(topic :: Nil)
      _ <- consume.commitSync()
      consumerRecords <- consume.poll(5.seconds)
      list <- record.getRecords(consumerRecords, topic)
      listTDS2 = list.map(record.getKeyValue)
                     .map(_.flatMap(testData.apply))
                     .map(_.flatMap(testData.subtract2))
                     .map(_.flatMap(td => testData.toString(td)))
                     
      _ = listTDS2.map(_.flatMap(l => bf.appendLine(file, l)))
    } yield listTDS2
  }

  val topic = "test"
  val path = "path/to/output/file"
  val output = program[ConsumerModule.Op](topic, path).interpret[Target]
}
