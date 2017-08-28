
import java.util.Properties
import java.util.logging.Logger

import Models.TwitterDb
import Services.{JsonHandler, JsonServices, TwitterOperations}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
class KafkaScalaProducer {
  val logger = Logger.getLogger(this.getClass.toString)

  def setProps(): Properties = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("client.id", "ScalaProducerExample")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("acks", "all")
    props.put("retries", "0")
    props.put("batch.size", "16384")
    props.put("linger.ms", "1")
    props.put("buffer.memory", "33554432")

    props
  }

  val twitterOperations = new TwitterOperations()
  val producer: KafkaProducer[Nothing, String] = new KafkaProducer[Nothing, String](setProps())
  val topic = "kafka-topic-kip"
  println(s"Sending Records in Kafka Topic [$topic]")

  def sendDataToTopic(username : String) ={
      val data = twitterOperations.getTweets(username)
      data.map{ tweetList=>
        tweetList.foreach { hashtag =>

          val tweetData: String = JsonServices.serializeJson(TwitterDb(username,hashtag))
          val record: ProducerRecord[Nothing, String] = new ProducerRecord(topic, tweetData)
          println(tweetData)
          producer.send(record)
        }
      }
  }
  producer.close()

}