package utils

implicit class RichPipes[Y](y: Y) {
  def |>[Z](f: Y => Z) = f(y)
  def &>[X, Z](f: (X, Y) => Z): (X => Z) = (x: X) => f(x, y)
}
