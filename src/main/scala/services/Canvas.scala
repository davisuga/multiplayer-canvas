package services
import entities.{Canvas, Event}
import cats.effect.IO

object Canvas {
  def read(canvasId: String) =
    models.redis.client.use { client =>
      given models.redis.RedisEnv = client
      models.redis.Canvas.read(canvasId)
    }
  def create(rows: Int, cols: Int, uuid: String = java.util.UUID.randomUUID().toString()) =
    val emptyCanvas = entities.Canvas(uuid, entities.Canvas.empty(cols, rows))
    models.redis.client.use { client =>
      given models.redis.RedisEnv = client
      models.redis.Canvas.create(emptyCanvas) >> services.addTopic(uuid) >> IO.pure(uuid)
    }
  def draw(canvas: Event.Draw) =
    models.redis.client.use { client =>
      given models.redis.RedisEnv = client
      models.redis.Canvas.draw(canvas)
    }
  def getIds() =
    models.redis.client.use { client =>
      given models.redis.RedisEnv = client
      models.redis.Canvas.getIds
    }
}
