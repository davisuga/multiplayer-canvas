package entities

case class Pixel(color: Color)
object Pixel {
  def empty = Pixel(Color.empty)
}
