package freestyleKafkaExample
package algebras

import types._

import freestyle._

case class TestData(k: String, v: Long) {
  def add2: TestData =
    this.copy(v = v + 2)

  def subtract2: TestData =
    this.copy(v = v - 2)

  override def toString: String =
    s"${k},${v}"
}

object TestData{
  def stringApply(str: String): TestData = {
    val arr = str.split(",")
    TestData(arr.head, arr.tail.head.toLong)
  }
}

@free trait TestDataF {
  def apply(tuple: (String, Long)): FS[TestData]
  def stringApply(str: String): FS[TestData]
  def toString(td: TestData): FS[String]
  def unapply(testData: TestData): FS[(String, Long)]
  def add2(testData: TestData): FS[TestData]
  def subtract2(testData: TestData): FS[TestData]
}
