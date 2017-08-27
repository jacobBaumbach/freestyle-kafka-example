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

  def program[F[_]](topic: String, path: String)(implicit PM: ProducerModule[F]) = {
      import PM.bftd._, PM._
  
    for {
      bfFile <- bf.file(path)
      lines <-  bf.lineIterator(bfFile)
      metaData = lines.map(testData.stringApply)
                      .map(_.flatMap(testData.add2))
                      .map(_.flatMap(testData.unapply))
                      .map(_.flatMap(kv => record.producerRecord(topic, kv._1, kv._2)))
                      .map(_.flatMap(produce.send))
      _ = metaData.map(_.flatMap(_ => produce.flush()))
    } yield metaData
  }

  val topic  = "test"
  val path   = "path/to/file"
  val output = program[ProducerModule.Op](topic, path).interpret[Target]
}
