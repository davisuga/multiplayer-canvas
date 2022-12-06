package entities
import entities.types.{Color, Pixel}
object Canvas {

  def createEmptyCanvas(rows: Int, cols: Int) =
    val emptyPixel = Pixel(Color(0, 0, 0))
    val emptyRow = List.fill(cols) { emptyPixel }

    List.fill(rows) {
      emptyRow
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
        row.zipWithIndex.map((elem, j) => if j == x && i == y then pixel else elem)
      )
    )

}
