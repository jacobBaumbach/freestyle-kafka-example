package freestyleKafkaExample
package record

import types._

import cats.implicits._

import freestyle._

import org.apache.kafka.clients.producer.ProducerRecord


@free trait ProducerRecordExample {
  def producerRecord(topic: String, key: String, value: Long): FS[ProducerRecord[String, Long]]
}

object prog{
  import freestyleKafkaExample.interpreters.TargetI._

  def program[F[_]](topic: String, k: String, v: Long)(implicit P: ProducerRecordExample[F]) = {
    import P._

    for{
      pr <- producerRecord(topic,k,v)
    } yield pr
  }


  val topic  = "test"
  val k      = "freestyle"
  val v      = 0L
  val output = program[ProducerRecordExample.Op](topic, k, v).interpret[Target]
}
