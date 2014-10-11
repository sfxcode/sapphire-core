package com.sfxcode.sapphire.core.controller

import scalafx.scene.Node
import scalafx.beans.property.Property
import scalafx.scene.control.{CheckBox, TextArea, TextField, Label}
import scalafx.Includes._

trait NodePropertyResolving {

  def resolve(node: Node): Option[Property[_,_]] = {
    node match {
      case label: Label => Some(label.textProperty())
      case textField: TextField => Some(textField.textProperty())
      case textArea: TextArea => Some(textArea.textProperty())
      case checkBox: CheckBox => Some(checkBox.selectedProperty())
      case _ => resolveCustomNode(node)
    }
  }

  def resolveCustomNode(node: Node): Option[Property[_,_]] = {
    node match {
      case _ => None
    }
  }

}
