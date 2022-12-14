package config

val redisUri = sys.env.get("REDIS_CONN_STRING") getOrElse "redis://localhost"
