package freestyleKafkaExample
package algebras

import types._

import freestyle._

case class TestData(k: String, v: Long){
  def add2: TestData =
    this.copy(v = v + 2)

  def subtract2: TestData =
    this.copy(v = v - 2)
}

@free trait TestDataF{
  def apply(tuple: (String, Long)): FS[TestData]
  def unapply(testData: TestData): FS[(String, Long)]
  def add2(testData: TestData): FS[TestData]
  def subtract2(testData: TestData): FS[TestData]
}
