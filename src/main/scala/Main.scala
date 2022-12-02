import zio._
import multiplayer_canvas.graphql._

import caliban.ZHttpAdapter
import io.netty.handler.codec.http.{HttpHeaderNames, HttpHeaderValues}
import zio._
import zio.stream._
import zhttp.http._

import zhttp.service.Server
import zhttp.socket.{WebSocketChannelEvent, WebSocketFrame}
import zhttp.service.ChannelEvent.ChannelRead
import zhttp.service.ChannelEvent
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
import multiplayer_canvas.types.Draw
import multiplayer_canvas.drawing_stream._
import multiplayer_canvas.types._

object MultiplayerCanvas extends ZIOAppDefault {
  private val graphiql = Http.fromStream(ZStream.fromResource("graphiql.html"))
  private val socket =
    for
      queue <- makeDrawingQueue()
      stream = getDrawingStream(queue)
      s = Http.collectZIO[WebSocketChannelEvent] {
        case ChannelEvent(ch, ChannelRead(WebSocketFrame.Text("subscribe"))) =>
          stream
            .foreach(d => ch.write(WebSocketFrame.text(d.asJson.toString)))
            *> ch.flush
        case ChannelEvent(ch, ChannelRead(WebSocketFrame.Text(text))) =>
          decode[Draw](text) match {
            case Left(_) =>
              ch.write(WebSocketFrame.text("failed to parse")) *> ch.flush
            case Right(value) =>
              multiplayer_canvas.draw.service
                .draw(queue)(value)
                .map(canvas =>
                  ch.write(WebSocketFrame.text(canvas.asJson.toString))
                )
                *> ch.flush

          }
      }
    yield s

  override val run =
    println("RUNNING THIS SHIT")

    (for {
      websockets: Http[Any, Throwable, WebSocketChannelEvent, Unit] <- socket
      interpreter <- getGraphQLInterpreter()
      _ <- Server
        .start(
          8088,
          Http.collectHttp[Request] {
            case _ -> !! / "api" / "graphql" =>
              ZHttpAdapter.makeHttpService(interpreter)
            case _ -> !! / "ws" / "graphql" =>
              ZHttpAdapter.makeWebSocketService(interpreter)
            case _ -> !! / "graphiql" => graphiql
            case _ -> !! / "ws"       => websockets.toSocketApp.toHttp

          }
        )
        .forever
    } yield ()).exitCode
}
