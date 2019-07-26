package com.sfxcode.sapphire.core.demo.tutorial.controller

import com.sfxcode.sapphire.core.controller.ViewController
import com.typesafe.scalalogging.LazyLogging
import javax.enterprise.context.ApplicationScoped
import javax.inject.Named

@Named
@ApplicationScoped
class StatusBarController extends ViewController with LazyLogging {

  //  val actionLabel: Label = new Label {
  //    text = "Status Bar Action Label ..."
  //    padding = Insets(5)
  //  }
  //
  //  val statusLabel: Label = new Label {
  //    text = "Status Bar Status Label ..."
  //    padding = Insets(5)
  //  }
  //
  //  rootPane = new HBox {
  //    id = "statusBar"
  //    padding = Insets(5)
  //    spacing = 10
  //    children = List(
  //      new Button {
  //        text = "Status Button 1"
  //        onAction = handleButtonAction(this)
  //      },
  //      new Button {
  //        text = "Status Button 2"
  //        onAction = handleButtonAction(this)
  //      },
  //      statusLabel,
  //      actionLabel)
  //  }
  //
  //  def handleButtonAction(button: Button): ActionEvent => Unit =
  //    (_: ActionEvent) => {
  //      logger.debug("%s".format(button.text))
  //      updateLabel(button)
  //    }
  //
  //  def updateLabel(button: Button): Unit = {
  //    actionLabel.text = "%s clicked".format(button.getText)
  //  }

}
