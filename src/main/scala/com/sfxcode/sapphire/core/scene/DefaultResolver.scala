package com.sfxcode.sapphire.core.scene

import javafx.beans.property.Property
import javafx.scene.Node
import javafx.scene.control._
import javafx.scene.text.Text

class DefaultResolver extends NodePropertyResolving {

  def resolve(node: Node): Option[Property[_]] = {
    node match {
      case label: Label               => Some(label.textProperty())
      case text: Text                 => Some(text.textProperty())
      case textField: TextField       => Some(textField.textProperty())
      case textArea: TextArea         => Some(textArea.textProperty())
      case datePicker: DatePicker     => Some(datePicker.valueProperty())
      case toggleButton: ToggleButton => Some(toggleButton.selectedProperty())
      case checkBox: CheckBox         => Some(checkBox.selectedProperty())
      case slider: Slider             => Some(slider.valueProperty())
      case comboBox: ComboBox[_]      => Some(comboBox.valueProperty())
      case choiceBox: ChoiceBox[_]    => Some(choiceBox.valueProperty())
      case spinner: Spinner[_]        => Some(spinner.getValueFactory.valueProperty())
      case _                          => None
    }
  }

}

object DefaultResolver {

  def apply(): DefaultResolver = new DefaultResolver()
}
