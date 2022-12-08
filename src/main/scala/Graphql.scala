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
  models.canvas.InMemory.getCanvas(id)

def putPiece(boardId: ID, value: Pixel, timestamp: Long) = ???

case class Mutations[F[_]](
  createCanvas: Size => F[Canvas]
)

case class Queries(canvas: (id: ID) => Option[Canvas])

val queries = Queries(getCanvas)
val mutations =
  Mutations(size => models.canvas.InMemory.createCanvas(size.rows, size.columns))

def api(implicit dispatcher: Dispatcher[cats.effect.IO]) = graphQL(
  RootResolver(queries, mutations)
)
