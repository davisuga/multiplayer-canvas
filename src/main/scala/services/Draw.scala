package multiplayer_canvas.draw.service

import multiplayer_canvas.drawing_stream._
import multiplayer_canvas.types._

def draw(value: Draw) =
  queueDraw(value)
  multiplayer_canvas.InMemory.writePixel(
    value.x,
    value.y,
    value.pixel,
    value.canvasId
  )
