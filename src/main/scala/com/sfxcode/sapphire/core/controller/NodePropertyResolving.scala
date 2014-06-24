package com.sfxcode.sapphire.core.controller

import javafx.scene.Node
import javafx.beans.property.Property
import javafx.scene.control.{CheckBox, TextArea, TextField, Label}


trait NodePropertyResolving {

  def resolve(node: Node): Option[Property[_]] = {
    node match {
      case label: Label => Some(label.textProperty())
      case textField: TextField => Some(textField.textProperty())
      case textArea: TextArea => Some(textArea.textProperty())
      case checkBox: CheckBox => Some(checkBox.selectedProperty())
      case _ => resolveCustomNode(node)
    }
  }

  def resolveCustomNode(node: Node): Option[Property[_]] = {
    node match {
      case _ => None
    }
  }

}
