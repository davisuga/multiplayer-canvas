import multiplayer_canvas.types._
import multiplayer_canvas._

object CanvasSpec extends ZIOSpecDefault {
  def spec =
    suite("CanvasSpec")(
      test("Draw a pixel") {
        val empty = Canvas("0", createEmptyCanvas(3, 4))
        val modified =
          addPixelToCanvas(empty, Pixel(Color(255, 255, 255)), 0, 0)
        assertTrue(
          modified == Canvas(
            "0",
            List(
              List(
                Pixel(Color(255, 255, 255)),
                Pixel(Color(0, 0, 0)),
                Pixel(Color(0, 0, 0)),
                Pixel(Color(0, 0, 0))
              ),
              List(
                Pixel(Color(0, 0, 0)),
                Pixel(Color(0, 0, 0)),
                Pixel(Color(0, 0, 0)),
                Pixel(Color(0, 0, 0))
              ),
              List(
                Pixel(Color(0, 0, 0)),
                Pixel(Color(0, 0, 0)),
                Pixel(Color(0, 0, 0)),
                Pixel(Color(0, 0, 0))
              )
            )
          )
        )
      }
    )
}
