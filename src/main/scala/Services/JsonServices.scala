package Services

import Models.TwitterDb
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

object JsonServices {

  def serializeJson(twitterDb: TwitterDb): String = {
    val objectMapper = new ObjectMapper() with ScalaObjectMapper
    objectMapper.registerModule(new DefaultScalaModule)
    objectMapper.writeValueAsString(twitterDb)
  }

  def deserializeJson(jsonData: String): TwitterDb = {
    import com.fasterxml.jackson.databind.ObjectMapper
    val mapper = new ObjectMapper() with ScalaObjectMapper
    mapper.registerModule(DefaultScalaModule)
    mapper.readValue[TwitterDb](jsonData)
  }

}