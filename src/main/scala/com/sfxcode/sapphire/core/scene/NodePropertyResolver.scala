package com.sfxcode.sapphire.core.scene

import scalafx.beans.property.Property
import javafx.scene.Node
import javafx.scene.control._
import scalafx.Includes._

class NodePropertyResolver {

  def resolve(node: Node): Option[Property[_, _ <: Any]] = {
    node match {
      case label: Label => Some(label.textProperty())
      case textField: TextField => Some(textField.textProperty())
      case textArea: TextArea => Some(textArea.textProperty())
      case checkBox: CheckBox => Some(checkBox.selectedProperty())
      case _ => resolveCustomNode(node)
    }
  }

  def resolveCustomNode(node: Node): Option[Property[_, _ <: Any]] = {
    node match {
      case _ => None
    }
  }
}

object NodePropertyResolver {
  def apply():NodePropertyResolver = {
    new NodePropertyResolver
  }
}
