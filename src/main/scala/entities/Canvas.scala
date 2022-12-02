package multiplayer_canvas

import multiplayer_canvas.types._

def createEmptyCanvas(rows: Int, cols: Int) = List.fill(rows) {
  List.fill(cols) { Pixel(Color(0, 0, 0)) }
}

def addPixelToCanvas(canvas: Canvas, pixel: Pixel, x: Int, y: Int) =
  Canvas(
    canvas.id,
    canvas.value.zipWithIndex.map((row, i) =>
      row.zipWithIndex.map((elem, j) =>
        if j == x && i == y then pixel else elem
      )
    )
  )
