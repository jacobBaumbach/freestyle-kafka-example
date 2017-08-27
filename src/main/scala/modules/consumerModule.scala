package freestyleKafkaExample
package modules

import algebras._
import algebras.ConsumerExample._

import freestyle._

@module trait ConsumerModule {
  val bftd: BFTD
  val record: ConsumerRecordExample
  val consume: kafkaConsumerProvider.Consumer
}
