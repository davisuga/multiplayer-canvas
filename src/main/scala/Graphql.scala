package ports.graphql

import caliban.GraphQL.graphQL
import caliban.RootResolver
import cats.effect.IO
import entities.*
import zio.ZIO
import zio.stream.ZStream
// Interop imports
import caliban.interop.cats.implicits.*
import cats.effect.std.Dispatcher

def getCanvas(id: ID) =
  services.Canvas.read(id)

case class Mutations[F[_]](
  createCanvas: Size => F[String]
)

case class Queries[F[_]](canvas: (id: ID) => F[Option[Canvas]])

val queries = Queries(getCanvas)
val mutations =
  Mutations(size => services.Canvas.create(size.rows, size.columns))

def api(implicit dispatcher: Dispatcher[cats.effect.IO]) = graphQL(
  RootResolver(queries, mutations)
)
