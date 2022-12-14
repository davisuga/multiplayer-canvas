package models.redis
import dev.profunktor.redis4cats.Redis
import dev.profunktor.redis4cats.effect.Log.Stdout._

import dev.profunktor.redis4cats.connection.RedisClient
import cats.effect.IO

val client = Redis[IO].utf8(config.redisUri)
val _ = println(config.redisUri)
