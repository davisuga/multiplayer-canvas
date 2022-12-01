# multiplayer-canvas
## sbt project compiled with Scala 3

### Usage

This is a normal sbt project. You can compile code with `sbt compile`, run it with `sbt run`, and `sbt console` will start a Scala 3 REPL.

For more information on the sbt-dotty plugin, see the
[scala3-example-project](https://github.com/scala/scala3-example-project/blob/main/README.md).
### Backend
- [ ] Basic GraphQL API
- [ ] Model User, Piece and Board
- [ ] Model PutPiece and GetBoard actions
- [ ] Persist board to Cassandra
- [ ] Subscribe to board <- get 
  - [ ] Read from SQS/Kafka/RabbitMQ/Redis and create ZStream
- [ ] Put piece
- [ ] Add Users

Get board at timestamp x, subscribe at x+y, get all actions from x to x+y