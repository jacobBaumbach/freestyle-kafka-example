package freestyleKafkaExample
package modules

import algebras._
import algebras.ProducerExample._

import freestyle._

@module trait ProducerModule {
  val bftd: BFTD
  val record: ProducerRecordExample
  val produce: kafkaProducerProvider.Producer
}
