package entities
import entities.types.{Pixel, Color}
object Canvas {

  def createEmptyCanvas(rows: Int, cols: Int) = List.fill(rows) {
    List.fill(cols) { Pixel(Color(0, 0, 0)) }
  }

  def addPixelToCanvas(
      canvas: entities.types.Canvas,
      pixel: Pixel,
      x: Int,
      y: Int
  ) =
    entities.types.Canvas(
      canvas.id,
      canvas.value.zipWithIndex.map((row, i) =>
        row.zipWithIndex.map((elem, j) =>
          if j == x && i == y then pixel else elem
        )
      )
    )

}
