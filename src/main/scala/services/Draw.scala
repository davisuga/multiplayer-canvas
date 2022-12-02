package multiplayer_canvas.draw.service

import multiplayer_canvas.drawing_stream._
import multiplayer_canvas.types._
import zio._
import util.chaining.scalaUtilChainingOps

def draw(queue: Queue[Draw])(value: Draw) =
  multiplayer_canvas.InMemory
    .writePixel(
      value.x,
      value.y,
      value.pixel,
      value.canvasId
    )
    .tap(_ => queueDraw(value)(queue))
