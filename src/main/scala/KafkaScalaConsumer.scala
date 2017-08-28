import Models.TwitterDb
import java.util.{Collections, Properties}
import Services.{CassandraProvider, JsonServices}
import scala.collection.JavaConverters._
import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}

class KafkaScalaConsumer extends CassandraProvider {

  def setProps(): Properties = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("group.id", "consumer-group-1")
    props.put("enable.auto.commit", "true")
    props.put("auto.commit.interval.ms", "1000")
    props.put("auto.offset.reset", "earliest")
    props.put("session.timeout.ms", "30000")

    props
  }

  val topic = "kafka-topic-kip"
  val consumer: KafkaConsumer[Nothing, String] = new KafkaConsumer[Nothing, String](setProps())
  consumer.subscribe(Collections.singletonList(topic))


  def sendDataToDatabase() = {
    while (true) {
      /**
        * Fetch data for the topics or partitions specified using one of the subscribe/assign APIs.
        */
      val records: ConsumerRecords[Nothing, String] = consumer.poll(100)
      cassandraConn.execute(s"CREATE TABLE IF NOT EXISTS tweets (id timestamp PRIMARY KEY, username text, hashtag text) ")

      for (record <- records.asScala) {

        lazy val twitterDb: TwitterDb = JsonServices.deserializeJson(record.value())
        cassandraConn.execute(
          s"INSERT INTO tweets (id,username,hashtag) VALUES (dateOf(now()),'${twitterDb.username}', '${twitterDb.hashtag}')")
        println(record)
      }
    }

  }
}