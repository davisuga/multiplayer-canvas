package multiplayer_canvas.types;
import zio.stream.ZStream
import zio._

type ID = String
case class Size(rows: Int, columns: Int)

case class Color(r: Int, g: Int, b: Int)
case class Pixel(color: Color)
case class Canvas(id: ID, value: List[List[Pixel]])

case class Draw(canvasId: ID, pixel: Pixel, timestamp: Int, x: Int, y: Int)
case class GetCanvas(canvasId: ID)
