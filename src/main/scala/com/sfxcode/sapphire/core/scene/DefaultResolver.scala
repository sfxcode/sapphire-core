package com.sfxcode.sapphire.core.scene

import javafx.scene.Node
import javafx.scene.control.{CheckBox, Label, TextArea, TextField}

import scalafx.Includes._
import scalafx.beans.property.Property

class DefaultResolver extends NodePropertyResolving{

  def resolve(node: Node): Option[Property[_, _ <: Any]] = {
    node match {
      case label: Label => Some(label.textProperty())
      case textField: TextField => Some(textField.textProperty())
      case textArea: TextArea => Some(textArea.textProperty())
      case checkBox: CheckBox => Some(checkBox.selectedProperty())
      case _ => None
    }
  }

}

object DefaultResolver {

  def apply():DefaultResolver = new DefaultResolver()
}
