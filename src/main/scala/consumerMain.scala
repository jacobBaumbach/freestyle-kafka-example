package freestyleKafkaExample

import interpreters.TargetI._
import consumers.ConsumerExample._
import types._

import cats._
import cats.implicits._

import freestyle._
import freestyle.implicits._

import scala.concurrent.duration._

object ConsumerRun extends App {
  def program[F[_]](topic: String)(implicit C: kafkaConsumerProvider.Consumer[F]) = {
    import C._

    for {
      _ <- subscribe(topic :: Nil)
      _ <- commitSync()
      consumerRecords <- poll(5.seconds)
    } yield consumerRecords
  }

  val topic = "test"
  val output = program[kafkaConsumerProvider.Consumer.Op](topic).interpret[Target]
}
