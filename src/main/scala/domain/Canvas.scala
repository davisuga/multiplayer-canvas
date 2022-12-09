package entities

import entities.{Color, ID, Pixel}

case class Canvas(id: ID, value: List[List[Pixel]])

object Canvas {

  def empty(rows: Int, cols: Int) =
    val emptyPixel = Pixel.empty
    val emptyRow = List.fill(cols) { emptyPixel }

    List.fill(rows) {
      emptyRow
    }

  def addPixel(
    canvas: Canvas,
    pixel: Pixel,
    x: Int,
    y: Int
  ) =
    canvas.copy(value = canvas.value.updated(x, canvas.value(x).updated(y, pixel)))

}
