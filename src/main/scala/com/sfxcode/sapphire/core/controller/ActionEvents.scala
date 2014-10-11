package com.sfxcode.sapphire.core.controller

import scalafx.scene.Node
import scalafx.event.ActionEvent
import scalafx.scene.control.{ChoiceBox, ComboBox}

trait ActionEvents {
  
  def actionSource[T <: Node](event: ActionEvent): T = event.source.asInstanceOf[T]

  def selectionFromActionEvent[S <: Any](event: ActionEvent): Option[S] = {
    actionSource[Node](event) match {
      case comboBox: ComboBox[S] => Some(comboBox.selectionModel().getSelectedItem)
      case choiceBox: ChoiceBox[S] => Some(choiceBox.getSelectionModel.getSelectedItem)
      case _ => None
    }
  }
}
