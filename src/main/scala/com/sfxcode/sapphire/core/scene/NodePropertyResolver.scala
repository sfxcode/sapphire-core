package com.sfxcode.sapphire.core.scene

import javafx.scene.Node
import javafx.beans.property._
import javafx.scene.control._

object NodePropertyResolver {

  def resolve(node: Node): Option[Property[_]] = {
    node match {
      case label: Label => Some(label.textProperty())
      case textField: TextField => Some(textField.textProperty())
      case textArea: TextArea => Some(textArea.textProperty())
      case checkBox: CheckBox => Some(checkBox.selectedProperty())
      case _ => None
    }
  }
}
