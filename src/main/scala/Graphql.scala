package multiplayer_canvas.graphql
import multiplayer_canvas.types._
import multiplayer_canvas.drawing_stream._

import caliban.GraphQL.graphQL
import caliban.RootResolver
import zio.ZIO
import zio.stream.ZStream
import multiplayer_canvas._

def getCanvas(id: ID) =
  InMemory.getCanvas(id)

def putPiece(boardId: ID, value: Pixel, timestamp: Long) = ???

case class Subscriptions(
    newDrawings: ZStream[Any, Nothing, Draw]
)
case class Mutations(
    draw: Draw => ZIO[Any, Throwable, Canvas],
    createCanvas: Size => ZIO[Any, Nothing, Canvas]
)

case class Queries(canvas: (id: ID) => Option[Canvas])

val queries = Queries(getCanvas)

def getGraphQLInterpreter() =
  for {
    queue <- makeDrawingQueue()
    mutations =
      Mutations(
        multiplayer_canvas.draw.service.draw(queue),
        (size => InMemory.createCanvas(size.rows, size.columns))
      )

    interpreter <- graphQL(
      RootResolver(queries, mutations)
    ).interpreter
  } yield (interpreter)
