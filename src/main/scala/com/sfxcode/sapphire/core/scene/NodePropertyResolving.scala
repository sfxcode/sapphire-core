package com.sfxcode.sapphire.core.scene

import javafx.scene.Node

import scalafx.beans.property.Property

trait NodePropertyResolving {
  def resolve(node: Node): Option[Property[_, _ <: Any]]
}
