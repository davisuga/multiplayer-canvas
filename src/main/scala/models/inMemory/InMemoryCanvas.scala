package models.canvas
import scala.collection.mutable._
import entities._
import scala.util.chaining._
import cats.effect.IO

sealed trait InMemoryCanvasError
case object FailedToUpdate extends InMemoryCanvasError
case object NoCanvas extends InMemoryCanvasError

object InMemory {
  def createCanvas(xSize: Int, ySize: Int) =
    val canvasPixels = Canvas.empty(xSize, ySize)
    val id = inMemoryBoard.knownSize.toString()
    val canvas = Canvas(id, canvasPixels)
    inMemoryBoard.addOne(id, canvas)
    services.addTopic(id) >>
      IO.pure(canvas)

  val getCanvas = inMemoryBoard.get

  def writePixel(
    x: Int,
    y: Int,
    pixel: Pixel,
    id: String
  ) =
    getCanvas(id) match {
      case Some(canvas) =>
        println(s"Update pixel: ${pixel}")

        val computedCanvas = Canvas
          .addPixel(canvas, pixel, x, y)

        inMemoryBoard
          .put(id, computedCanvas)
          .tap(_ => println(s"Update canvas: ${computedCanvas}"))
          .map(_ => computedCanvas)

      case None => None
    }

}
val inMemoryBoard = Map.empty[String, Canvas]
