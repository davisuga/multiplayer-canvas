package multiplayer_canvas.types;
import zio.stream.ZStream
import zio._

type ID = String

case class Color(r: Int, g: Int, b: Int)
case class Pixel(x: Int, y: Int, color: Color)
case class Canvas(id: ID, value: List[List[Pixel]])

trait Action
case class Draw(canvasId: ID, value: Pixel, timestamp: Int) extends Action
case class GetCanvas(canvasId: ID) extends Action
