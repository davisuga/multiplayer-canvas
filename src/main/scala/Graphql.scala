package multiplayer_canvas.graphql
import multiplayer_canvas.types._
import multiplayer_canvas.drawing_stream._

import caliban.GraphQL.graphQL
import caliban.RootResolver
import zio.ZIO
import zio.stream.ZStream
import multiplayer_canvas._

def getCanvas(id: ID) =
  println("getting board!")
  CanvasStorage.getCanvas(id)

def putPiece(boardId: ID, value: Pixel, timestamp: Long) = ???

case class Subscriptions(
    newDrawings: ZStream[Any, Nothing, Draw]
)

case class Mutations(addDraw: Draw => ZIO[Any, Nothing, Boolean])

case class Queries(board: (id: ID) => Option[Canvas])

val queries = Queries(getCanvas)
val mutations = Mutations(addDraw)

def getGraphQLInterpreter() =
  for {
    drawingStream <- getDrawingStream()
    subscriptions = Subscriptions(drawingStream)
    interpreter <- graphQL(
      RootResolver(queries, mutations, subscriptions)
    ).interpreter
  } yield (interpreter)
