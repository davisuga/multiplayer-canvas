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
import entities.types.{Enter, Event}
import cats.effect._, org.http4s._, org.http4s.dsl.io._

val M = server_p.Server
