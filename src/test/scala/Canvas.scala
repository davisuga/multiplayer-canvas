import entities.*
import cats.effect.{IO, SyncIO}
import munit.CatsEffectSuite

class CanvasSuite extends munit.FunSuite {
  test("Draw a pixel") {
    val empty = Canvas("0", Canvas.empty(3, 4))
    val modified =
      Canvas.addPixel(empty, Pixel(Color.black), 0, 0)
    assertEquals(
      modified,
      Canvas(
        "0",
        List(
          List(
            Pixel(Color.black),
            Pixel(Color.empty),
            Pixel(Color.empty),
            Pixel(Color.empty)
          ),
          List(
            Pixel(Color.empty),
            Pixel(Color.empty),
            Pixel(Color.empty),
            Pixel(Color.empty)
          ),
          List(
            Pixel(Color.empty),
            Pixel(Color.empty),
            Pixel(Color.empty),
            Pixel(Color.empty)
          )
        )
      )
    )
  }
}

class CanvasServicesSuite extends CatsEffectSuite {
  test("Create a canvas") {
    services.Canvas.create(1028, 1028, "testing").assertEquals("testing")
  }
  test("List canvas ids") {
    services.Canvas.create(1028, 1028, "testing2")
      >> services.Canvas.getIds() assertEquals Seq("testing", "testing2")
  }
}
