package multiplayer_canvas.graphql
import multiplayer_canvas.types._
import multiplayer_canvas.drawing_stream._

import caliban.GraphQL.graphQL
import caliban.RootResolver
import zio.ZIO
import zio.stream.ZStream
import multiplayer_canvas._
import cats.effect.IO

def getCanvas(id: ID) =
  InMemory.getCanvas(id)

def putPiece(boardId: ID, value: Pixel, timestamp: Long) = ???

case class Mutations(
    createCanvas: Size => Unit
)

case class Queries(canvas: (id: ID) => Option[Canvas])

val queries = Queries(getCanvas)
val mutations =
  Mutations((size => InMemory.createCanvas(size.rows, size.columns)))

val api = graphQL(
  RootResolver(queries, mutations)
)
