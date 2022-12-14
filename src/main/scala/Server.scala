package http

import caliban.interop.cats.implicits.*
import caliban.{CalibanError, GraphQLInterpreter, Http4sAdapter}
import cats.data.Kleisli
import cats.effect.std.{Dispatcher, Queue}
import cats.effect.unsafe.implicits.global
import cats.effect.{ExitCode, IO, *}
import entities.*
import Event.*

import fs2.concurrent.Topic
import io.circe.*
import io.circe.generic.auto.*
import io.circe.parser.*
import io.circe.syntax.*
import fs2.*
import org.http4s.*
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.dsl.io.*
import org.http4s.implicits.*
import org.http4s.server.Router
import org.http4s.server.middleware.CORS
import org.http4s.server.websocket.{WebSocketBuilder, WebSocketBuilder2}
import org.http4s.websocket.WebSocketFrame
import org.http4s.websocket.WebSocketFrame.{Close, Text}
import zio.internal.Platform
import zio.{Runtime, *}

implicit val zioRuntime: Runtime[Any] = Runtime.default

def makeWsRoutes(wsBuilder: WebSocketBuilder2[cats.effect.IO]) =
  HttpRoutes.of[IO] {
    case GET -> Root / topicName => {
      services.DrawEvent.makeWsHandles(topicName) match {
        case Some(send, receive) =>
          wsBuilder.build(receive, send).to
        case None => IO.pure(Response.notFound)
      }
    }
  }

object Server extends IOApp:

  override def run(args: List[String]) =
    Dispatcher[IO].use { implicit dispatcher =>
      for
        interpreter <- ports.graphql.api.interpreterAsync[IO]
        graphQLEndpoint = "/api/graphql" ->
          (CORS.policy(
            Http4sAdapter.makeHttpServiceF[IO, Any, CalibanError](
              interpreter
            )
          ))

        _ <- BlazeServerBuilder[IO]
          .bindHttp(8088, "localhost")
          .withHttpWebSocketApp(wsBuilder =>
            Router[IO](
              graphQLEndpoint,
              "/ws/graphql" ->
                CORS.policy(
                  Http4sAdapter.makeWebSocketServiceF[IO, Any, CalibanError](
                    wsBuilder,
                    interpreter
                  )
                ),
              "/graphiql" ->
                Kleisli.liftF(StaticFile.fromResource("/graphiql.html", None)),
              "/ws" -> makeWsRoutes(wsBuilder)
            ).orNotFound
          )
          .serve
          .compile
          .drain
      yield ExitCode.Success
    }
