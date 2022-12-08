package draw.service

import entities._
import zio._
import util.chaining.scalaUtilChainingOps

def draw(queue: Queue[Draw])(value: Draw) =
  models.canvas.InMemory
    .writePixel(
      value.x,
      value.y,
      value.pixel,
      value.canvasId
    )
  // .tap(_ => queueDraw(value)(queue))
