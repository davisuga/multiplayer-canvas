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
    draw: Draw => Option[multiplayer_canvas.types.Canvas],
    createCanvas: Size => Canvas
)

case class Queries(canvas: (id: ID) => Option[Canvas])

val queries = Queries(getCanvas)
val mutations =
  Mutations(
    multiplayer_canvas.draw.service.draw,
    (size => InMemory.createCanvas(size.rows, size.columns))
  )

def getGraphQLInterpreter() =
  for {
    drawingStream <- getDrawingStream()
    subscriptions = Subscriptions(drawingStream)
    interpreter <- graphQL(
      RootResolver(queries, mutations, subscriptions)
    ).interpreter
  } yield (interpreter)
