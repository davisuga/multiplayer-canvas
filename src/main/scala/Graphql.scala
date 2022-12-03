package ports.graphql

import entities.types._

import caliban.GraphQL.graphQL
import caliban.RootResolver
import zio.ZIO
import zio.stream.ZStream
import entities._
import cats.effect.IO

def getCanvas(id: ID) =
  models.canvas.InMemory.getCanvas(id)

def putPiece(boardId: ID, value: Pixel, timestamp: Long) = ???

case class Mutations(
    createCanvas: Size => Unit
)

case class Queries(canvas: (id: ID) => Option[Canvas])

val queries = Queries(getCanvas)
val mutations =
  Mutations(
    (size => models.canvas.InMemory.createCanvas(size.rows, size.columns))
  )

val api = graphQL(
  RootResolver(queries, mutations)
)
