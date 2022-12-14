package services

import entities._
import Event.*
import cats.implicits.catsSyntaxFlatMapOps

import util.chaining.scalaUtilChainingOps
import models.CanvasRepo
import cats.effect.IO

object Draw {

  def draw(value: Draw) =
    models.redis.client.use { client =>
      given models.redis.RedisEnv = client
      models.canvas.InMemory
        .writePixel(
          value.x,
          value.y,
          value.pixel,
          value.canvasId
        )
        .map(_ => models.redis.Canvas.draw(value))
        .getOrElse(IO.pure(()))
    }
    // .tap(_ => queueDraw(value)(queue))
}
