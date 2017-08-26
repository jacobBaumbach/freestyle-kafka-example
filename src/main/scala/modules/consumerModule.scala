package freestyleKafkaExample
package modules

import algebras._
import algebras.ConsumerExample._

import freestyle._

@module trait ConsumerModule {
  val testData: TestDataF
  val record: ConsumerRecordExample
  val consume: kafkaConsumerProvider.Consumer
}
