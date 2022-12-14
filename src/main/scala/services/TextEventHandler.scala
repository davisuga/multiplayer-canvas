package services
import scala.collection.mutable._

import fs2.Stream
import fs2.concurrent.Topic
import org.http4s.websocket.WebSocketFrame
import cats.effect.IO
import entities._
import io.circe.generic.auto.*
import io.circe.syntax.*
import entities.*
import entities.Event.*

def makeStreamOfTopic(
  topic: Topic[IO, WebSocketFrame.Text],
  handler: Event => IO[Any] = _ => IO.pure(())
)(incomingStream: Stream[IO, WebSocketFrame]) =

  val entryStream = Stream.emits(Seq(Enter))

  val parsedWebSocketInput = incomingStream.collect { case WebSocketFrame.Text(text, _) =>
    entities.Event.fromJson(text)
  }

  val publishEventToTopic = (event: Event) =>
    topic.publish1(WebSocketFrame.Text(event.asJson.toString))
      >> handler(event)
      >> IO.pure(())

  (entryStream ++ parsedWebSocketInput).evalMap(publishEventToTopic)

val topicMap = Map.empty[String, Topic[cats.effect.IO, WebSocketFrame.Text]]

def addTopic(id: String) =
  Topic[cats.effect.IO, WebSocketFrame.Text].map(topicMap.addOne(id, _))

object DrawEvent:
  def makeWsHandles(
    topicName: String
  ) =
    topicMap
      .get(topicName)
      .map(topic =>
        (makeStreamOfTopic(topic,
                           {
                             case draw: Draw =>
                               services.Draw.draw(draw)
                             case _ => IO.pure(())
                           }
         ),
         topic.subscribe(10)
        )
      )
