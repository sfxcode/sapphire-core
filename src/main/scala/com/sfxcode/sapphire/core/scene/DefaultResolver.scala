package com.sfxcode.sapphire.core.scene

import javafx.scene.Node
import javafx.scene.control._
import javafx.scene.text.Text

import scalafx.Includes._
import scalafx.beans.property.Property

class DefaultResolver extends NodePropertyResolving{

  def resolve(node: Node): Option[Property[_, _ <: Any]] = {
    node match {
      case label: Label => Some(label.textProperty())
      case text: Text => Some(text.textProperty())
      case textField: TextField => Some(textField.textProperty())
      case textArea: TextArea => Some(textArea.textProperty())
      case checkBox: CheckBox => Some(checkBox.selectedProperty())
      case slider: Slider => Some(slider.valueProperty())
      case comboBox: ComboBox[_] => Some(comboBox.valueProperty())
      case choiceBox: ChoiceBox[_] => Some(choiceBox.valueProperty())
      case spinner: Spinner[_] => Some(spinner.getValueFactory.valueProperty())
      case _ => None
    }
  }

}

object DefaultResolver {

  def apply():DefaultResolver = new DefaultResolver()
}
