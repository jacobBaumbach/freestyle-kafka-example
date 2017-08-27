package freestyleKafkaExample
package algebras

import types._

import better.files.File

import freestyle._

@free trait BetterFiles {
  def file(path: String): FS[File]
  def lineIterator(file: File): FS[Iterator[String]]
  def appendLine(file: File, line: String): FS[File]
}

