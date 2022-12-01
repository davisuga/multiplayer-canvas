package multiplayer_canvas
import scala.collection.mutable._
import multiplayer_canvas.types.Canvas

object CanvasStorage {
  val addCanvas = inMemoryBoard.addOne
  val getCanvas = inMemoryBoard.get
}
val inMemoryBoard = Map.empty[String, Canvas]
