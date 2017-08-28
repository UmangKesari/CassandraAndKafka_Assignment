import sbt.Keys.libraryDependencies

name := "Kafka_Assignment"

version := "1.0"

scalaVersion := "2.12.3"

libraryDependencies += "org.apache.kafka" % "kafka-clients" % "0.10.1.0"

libraryDependencies += "org.twitter4j" % "twitter4j-core" % "4.0.6"

libraryDependencies ++= Seq(
  "com.datastax.cassandra" % "cassandra-driver-extras" % "3.0.0",
  "com.typesafe"               %  "config"           % "1.3.1"
)

libraryDependencies ++=Seq(
  "com.fasterxml.jackson.core" % "jackson-core" % "2.9.0",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.0"
)


    