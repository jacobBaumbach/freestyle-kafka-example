package freestyleKafkaExample
package testdata

import freestyle._

case class TestData(k: String, v: Long){
  def add2: TestData =
    this.copy(v = v + 2)

  def subtract2: TestData =
    this.copy(v = v - 2)
}
/*
 * producerRecord add scala prim to java prim
 * consumerRecord add scala prim to java prim
 * add TestData interpreter
 * convert (String, Long) to TestData
 */
@free trait TestDataF{
  def apply(tuple: (String, Long)): FS[TestData]
  def unapply(testData: TestData): FS[(String, Long)]
  def add2(testData: TestData): FS[TestData]
  def subtract2(testData: TestData): FS[TestData]
}
