package models.Redis

import cats.effect._
import cats.implicits._
import dev.profunktor.redis4cats.Redis
import dev.profunktor.redis4cats.effect.Log.Stdout._
import entities.Canvas
import dev.profunktor.redis4cats.RedisCommands
import dev.profunktor.redis4cats.algebra.BitCommandOperation

object Canvas {

  def create(implicit redis: RedisCommands[cats.effect.IO, String, String], canvas: Canvas) =
    for {
      _ <- redis.lPush(canvas.id, canvas.value.flatMap(_.map(x => x.color))*)
    } yield ()

  def update(implicit redis: RedisCommands[cats.effect.IO, String, String], canvas: Canvas, rows: Int, cols: Int) =
    for {
      _ <- redis.del(canvas.id)
      _ <- create(redis, canvas)
    } yield ()

}
