package entities
import io.circe.*
import io.circe.generic.auto.*
import io.circe.parser.*
import io.circe.syntax.*
import entities.{Color, ID, Pixel}
import utils.*

import util.chaining.scalaUtilChainingOps

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
  ) = canvas
    .value(x)
    .updated(y, pixel)
    .pipe(canvas.value.updated(x, _))
    .pipe(newVal => canvas.copy(value = newVal))

  def fromJson(src: String) = decode[Canvas](src).toOption
}
