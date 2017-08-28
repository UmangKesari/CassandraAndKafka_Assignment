package Services

import twitter4j.TwitterFactory

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class TwitterOperations {


  def getTweets(username : String): Future[List[String]] = {
    Future {

      val tweets = TwitterFactory.getSingleton
      val tweetsList = tweets.timelines().getUserTimeline(username).asScala.toList.flatMap{
      post =>post.getHashtagEntities.toList.map(_.getText)
      }
      tweetsList
    }

    }

}
