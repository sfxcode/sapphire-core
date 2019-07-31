package com.sfxcode.sapphire.core.scene

import javafx.beans.property.Property
import javafx.scene.Node

trait NodePropertyResolving {

  def resolve(node: Node): Option[Property[_]]

}
