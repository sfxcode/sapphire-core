package com.sfxcode.sapphire.core.demo.tutorial.controller

import com.sfxcode.sapphire.core.controller.ViewController
import com.typesafe.scalalogging.LazyLogging
import javafx.geometry.Insets
import javafx.scene.control.{ Button, Label }
import javafx.scene.layout.HBox

class StatusBarController extends ViewController with LazyLogging {

  rootPane = new HBox()

  val actionLabel: Label = new Label("Status Bar Action Label ...")
  actionLabel.setPadding(new Insets(5))

  val statusLabel: Label = new Label("Status Bar Status Label ...")
  statusLabel.setPadding(new Insets(5))

  val statusButton = new Button("Status Button 1")
  statusButton.setOnAction { _ =>
    logger.debug("%s".format(statusButton.getText))
    updateLabel(statusButton)
  }

  val statusButton2 = new Button("Status Button 2")
  statusButton2.setOnAction { _ =>
    logger.debug("%s".format(statusButton2.getText))
    updateLabel(statusButton2)
  }

  val box = new HBox()
  box.setId("statusBar")
  box.setPadding(new Insets(10))
  box.setSpacing(10.0)

  box.getChildren.addAll(statusButton, statusButton2, statusLabel, actionLabel)

  rootPane = box

  def updateLabel(button: Button): Unit = {
    val str = i18n("clickedMessage", button.getText, "!")
    actionLabel.setText(str)
  }

}
