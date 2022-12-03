package multiplayer_canvas.types;
import zio.stream.ZStream
import zio._

type ID = String
case class Size(rows: Int, columns: Int)

case class Color(r: Int, g: Int, b: Int)
case class Pixel(color: Color)
case class Canvas(id: ID, value: List[List[Pixel]])

sealed trait Event
case class Draw(canvasId: ID, pixel: Pixel, timestamp: Int, x: Int, y: Int)
    extends Event
case class Enter() extends Event
case class Unknown() extends Event

case class GetCanvas(canvasId: ID)
