package models.redis

import cats.effect._
import cats.implicits._
import dev.profunktor.redis4cats.Redis
import dev.profunktor.redis4cats.effect.Log.Stdout._
import entities.{Canvas, Event}

import dev.profunktor.redis4cats.RedisCommands
import dev.profunktor.redis4cats.algebra.BitCommandOperation
import models.CanvasRepo

type RedisEnv = RedisCommands[cats.effect.IO, String, String]
val canvasIdListKey = "canvases"
object Canvas extends CanvasRepo[IO, RedisEnv]:

  def create(canvas: Canvas)(using redis: RedisEnv) =
    redis.lPush(canvas.id, canvas.value.flatMap(_.map(_.color))*)
      >> redis.sAdd(canvasIdListKey, canvas.id)
      >> IO.pure(())

  def update(canvas: Canvas, rows: Int, cols: Int)(using redis: RedisEnv) =
    redis.del(canvas.id)
      >> create(canvas)

  def draw(action: Event.Draw, width: Int = 1024)(using redis: RedisEnv) =
    redis.lSet(action.canvasId, action.x + action.y * width, action.pixel.color)

  def read(canvasId: String)(using redis: RedisEnv) =
    redis.lRange(canvasId, 0, -1) map (_ => Some(entities.Canvas(canvasId, List())))

  def getIds(using redis: RedisEnv) = redis.sMembers(canvasIdListKey)

def makeCanvasValue(src: List[String]) =
  Array.emptyBooleanArray
