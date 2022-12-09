import entities.*
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
