package multiplayer_canvas

import multiplayer_canvas.types._

def addPixelToCanvas(canvas: Canvas, pixel: Pixel, x: Int, y: Int) =
  Canvas(
    canvas.id,
    canvas.value.zipWithIndex.map((row, i) =>
      row.zipWithIndex.map((elem, j) =>
        if j == x && i == y then pixel else elem
      )
    )
  )
