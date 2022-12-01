import zio._
import multiplayer_canvas.graphql._

import caliban.ZHttpAdapter
import io.netty.handler.codec.http.{HttpHeaderNames, HttpHeaderValues}
import zio._
import zio.stream._
import zhttp.http._
import zhttp.service.Server

object MultiplayerCanvas extends ZIOAppDefault {
  private val graphiql = Http.fromStream(ZStream.fromResource("graphiql.html"))

  override val run =
    println("RUNNING THIS SHIT")
    (for {
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
          }
        )
        .forever
    } yield ()).exitCode
}
