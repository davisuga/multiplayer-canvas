package multiplayer_canvas.drawing_stream
import zio._
import zio.Console._
import zio.stream._
import zio.Duration._
import multiplayer_canvas.types._

private val queue = Queue.bounded[Draw](100)

def getDrawingStream() = queue.map(queue => ZStream.fromQueue(queue))

def subscribe(handler: (Draw => ZIO[Any, Nothing, Any])) =
  getDrawingStream().flatMap(_.foreach(handler))

def queueDraw(draw: Draw) = queue.flatMap(q => q.offer(draw))
