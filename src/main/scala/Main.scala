import caliban.{CalibanError, GraphQLInterpreter, Http4sAdapter}
import caliban.interop.cats.implicits.*
import cats.data.Kleisli
import cats.effect.std.{Dispatcher, Queue}
import cats.effect.unsafe.implicits.global
import cats.effect.{ExitCode, IO, *}
import io.circe.*
import io.circe.generic.auto.*
import io.circe.parser.*
import io.circe.syntax.*
import fs2.{Stream, *}
import fs2.concurrent.Topic
import multiplayer_canvas.drawing_stream.*
import multiplayer_canvas.graphql.*
import multiplayer_canvas.types.*
import org.http4s.StaticFile
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.implicits.*
import org.http4s.server.Router
import org.http4s.server.middleware.CORS
import org.http4s.server.websocket.{WebSocketBuilder, WebSocketBuilder2}
import org.http4s.websocket.WebSocketFrame
import org.http4s.websocket.WebSocketFrame.{Close, Text}
import zio.{Runtime, *}
import zio.internal.Platform

object ExampleAppF extends IOApp:

  implicit val zioRuntime: Runtime[Any] = Runtime.default

  override def run(args: List[String]) =
    Dispatcher[IO].use { implicit dispatcher =>
      for
        topic <- Topic[IO, WebSocketFrame.Text]
        interpreter <- api.interpreterAsync[IO]
        _ <- BlazeServerBuilder[IO]
          .bindHttp(8088, "localhost")
          .withHttpWebSocketApp(wsBuilder =>
            Router[IO](
              "/api/graphql" ->
                CORS.policy(
                  Http4sAdapter.makeHttpServiceF[IO, Any, CalibanError](
                    interpreter
                  )
                ),
              "/ws/graphql" ->
                CORS.policy(
                  Http4sAdapter.makeWebSocketServiceF[IO, Any, CalibanError](
                    wsBuilder,
                    interpreter
                  )
                ),
              "/graphiql" ->
                Kleisli.liftF(StaticFile.fromResource("/graphiql.html", None)),
              "/ws" -> {

                val toClient = topic.subscribe(100)

                def fromClient(
                    wsfStream: Stream[IO, WebSocketFrame]
                ): Stream[IO, Unit] = {

                  val entryStream: Stream[IO, Event] =
                    Stream.emits(Seq(Enter()))

                  val parsedWebSocketInput: Stream[IO, Event] =
                    wsfStream
                      .collect { case Text(text, _) =>
                        decode[Draw](text).getOrElse(Unknown(text))
                      }

                  (entryStream ++ parsedWebSocketInput).evalMap(event =>
                    topic.publish1(Text(event.asJson.toString)) >> IO.pure(())
                  )
                }

                wsBuilder.build(toClient, fromClient).to
              }
            ).orNotFound
          )
          .serve
          .compile
          .drain
      yield ExitCode.Success
    }
