package entities

import io.circe.*
import io.circe.generic.auto.*
import io.circe.parser.*
import io.circe.syntax.*

import entities.ID

enum Event:
  case Draw(canvasId: ID, pixel: Pixel, timestamp: Int, x: Int, y: Int)
  case Enter
  case Exit
  case Unknown(raw: String)

object Event {

  def fromJson(src: String) = decode[Draw](src).getOrElse(Unknown(src))

}
