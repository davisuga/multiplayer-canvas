package entities

import io.circe.*
import io.circe.generic.auto.*
import io.circe.parser.*
import io.circe.syntax.*

import entities.ID

sealed trait Event

case class Draw(canvasId: ID, pixel: Pixel, timestamp: Int, x: Int, y: Int) extends Event
case class Enter() extends Event
case class Unknown(raw: String) extends Event

object Event {

  def parse(src: String) = decode[Draw](src).getOrElse(Unknown(src))

}
