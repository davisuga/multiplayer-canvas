package entities

import io.circe.*
import io.circe.generic.auto.*
import io.circe.parser.*
import io.circe.syntax.*
import entities.types.{Draw, Unknown}

object Event {

  def parse(src: String) = decode[Draw](src).getOrElse(Unknown(src))

}
