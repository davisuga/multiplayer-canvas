package multiplayer_canvas
import scala.collection.mutable._
import multiplayer_canvas.types._
import scala.util.chaining._
import zio.ZIO

sealed trait InMemoryCanvasError
case object FailedToUpdate extends InMemoryCanvasError
case object NoCanvas extends InMemoryCanvasError

object InMemory {
  def createCanvas(xSize: Int, ySize: Int) =
    val canvasPixels = createEmptyCanvas(xSize, ySize)
    val id = inMemoryBoard.knownSize.toString()
    val canvas = Canvas(id, canvasPixels)
    inMemoryBoard.addOne(id, canvas)
    ZIO.succeed(canvas)

  val getCanvas = inMemoryBoard.get

  def writePixel(
      x: Int,
      y: Int,
      pixel: Pixel,
      id: String
  ): ZIO[Any, Throwable, Canvas] =
    getCanvas(id) match {
      case Some(canvas) =>
        println(s"Update pixel: ${pixel}")

        val computedCanvas = multiplayer_canvas
          .addPixelToCanvas(canvas, pixel, x, y)

        inMemoryBoard
          .put(id, computedCanvas)
          .tap(_ => println(s"Update canvas: ${computedCanvas}"))
          .map(_ => ZIO.succeed(computedCanvas))
          .getOrElse(ZIO.fail(Exception(FailedToUpdate.toString())))

      case None => ZIO.fail(Exception(NoCanvas.toString()))
    }

}
val inMemoryBoard = Map.empty[String, Canvas]
