package com.sfxcode.sapphire.core.controller

import javafx.event.ActionEvent
import javafx.scene.Node
import javafx.scene.control.{ ChoiceBox, ComboBox, ListView }

trait ActionEvents {

  def selectionFromActionEvent[S <: Any](event: ActionEvent): Option[S] =
    actionSource[Node](event) match {
      case listView: ListView[S] => Some(listView.getSelectionModel.getSelectedItem)
      case comboBox: ComboBox[S] => Some(comboBox.getSelectionModel.getSelectedItem)
      case choiceBox: ChoiceBox[S] => Some(choiceBox.getSelectionModel.getSelectedItem)
      case _ => None
    }

  def actionSource[T <: Node](event: ActionEvent): T = event.getSource.asInstanceOf[T]
}
