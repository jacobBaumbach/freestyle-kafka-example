name := "freestyle-kafka-example"

scalaVersion := "2.12.2"

version := "0.0.1"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

val freestyleKafkaVersion = "0.0.1-SNAPSHOT"
val kafkaVersion     = "0.11.0.0"

libraryDependencies ++= Seq(
  "io.frees"         %% "freestyle-kafka-core" % freestyleKafkaVersion,
  "org.apache.kafka" %  "kafka-clients"        % kafkaVersion
)

addCompilerPlugin("org.scalameta" % "paradise" % "3.0.0-M9" cross CrossVersion.full)
