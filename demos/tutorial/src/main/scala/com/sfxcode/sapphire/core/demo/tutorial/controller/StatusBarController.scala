package com.sfxcode.sapphire.core.demo.tutorial.controller

import javax.enterprise.context.ApplicationScoped
import javax.inject.Named

import com.sfxcode.sapphire.core.controller.ViewController
import com.typesafe.scalalogging.LazyLogging

import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.geometry.Insets
import scalafx.scene.control.{ Button, Label }
import scalafx.scene.layout.HBox

@Named
@ApplicationScoped
class StatusBarController extends ViewController with LazyLogging {

  val actionLabel = new Label {
    text = "Status Bar Action Label ..."
    padding = Insets(5)
  }

  val statusLabel = new Label {
    text = "Status Bar Status Label ..."
    padding = Insets(5)
  }

  rootPane = new HBox {
    padding = Insets(5)
    spacing = 10
    children = List(
      new Button {
        text = "Status Button 1"
        onAction = handleButtonAction(this)
      },
      new Button {
        text = "Status Button 2"
        onAction = handleButtonAction(this)
      },
      statusLabel,
      actionLabel)
  }

  def handleButtonAction(button: Button) =
    (_: ActionEvent) => {
      logger.debug("%s".format(button.text))
      updateLabel(button)
    }

  def updateLabel(button: Button): Unit = {
    actionLabel.text = "%s clicked".format(button.getText)
  }

}
