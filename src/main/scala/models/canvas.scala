package models

import entities.{Canvas, Event}

trait CanvasRepo[F[_], Env]:
  def create(canvas: Canvas)(using env: Env): F[Unit]
  def update(canvas: Canvas, rows: Int, cols: Int)(using env: Env): F[Unit]
  def draw(action: Event.Draw, width: Int = 1024)(using env: Env): F[Unit]
  def read(canvasId: String)(using env: Env): F[Option[Canvas]]
