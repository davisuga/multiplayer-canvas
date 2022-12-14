package services
import entities.{Canvas, Event}
import cats.effect.IO

object Canvas {
  def read(canvasId: String) =
    models.redis.client.use { client =>
      given models.redis.RedisEnv = client
      models.redis.Canvas.read(canvasId)
    }
  def create(rows: Int, cols: Int) =
    val id = java.util.UUID.randomUUID().toString()
    val emptyCanvas = entities.Canvas(id, entities.Canvas.empty(cols, rows))
    models.redis.client.use { client =>
      given models.redis.RedisEnv = client
      models.redis.Canvas.create(emptyCanvas) >> services.addTopic(id) >> IO.pure(id)
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
