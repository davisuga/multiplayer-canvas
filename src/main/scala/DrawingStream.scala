package multiplayer_canvas.drawing_stream
import zio._
import zio.Console._
import zio.stream._
import zio.Duration._
import multiplayer_canvas.types._

def getDrawingStream(queue: Queue[Draw]) =
  println("getting draw stream")
  ZStream
    .fromQueue(queue)
    .tap(_ => ZIO.succeed(println("got drawing stream")))

def makeDrawingQueue() =
  println("getting draw stream")
  Queue.bounded[Draw](100)

def subscribe(handler: (Draw => ZIO[Any, Nothing, Any]))(
    stream: ZStream[Any, Nothing, Draw]
) =
  stream.foreach(handler)

def queueDraw(draw: Draw)(queue: Queue[Draw]) =
  println("Queuing draw...")
  queue.offer(draw).tap(_ => queue.size.map(println))
