package com.sfxcode.sapphire.core.demo.windows.controller

import com.sfxcode.sapphire.core.controller.ViewController
import com.sfxcode.sapphire.core.demo.windows.ApplicationController
import com.typesafe.scalalogging.LazyLogging
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Label

class MainViewController extends ViewController with LazyLogging {

  @FXML
  var windowLabel: Label = _

  override def startup() {
    logger.debug("class: " + this)
  }

  override def didGainVisibilityFirstTime() {
    super.didGainVisibility()
  }

  def applicationController: ApplicationController = windowController.get.asInstanceOf[ApplicationController]

  // #actionShowSecondWindow
  def actionShowSecondWindow(event: ActionEvent): Unit = {
    val x = applicationController.stage.getX + applicationController.stage.getWidth
    val y = applicationController.stage.getY
    applicationController.secondWindowController.show(x, y)
  }
  // #actionShowSecondWindow

  // #actionCloseSecondWindow
  def actionCloseSecondWindow(event: ActionEvent): Unit =
    applicationController.secondWindowController.close()
  // #actionCloseSecondWindow

  // #actionShowModalWindow
  def actionShowModalWindow(event: ActionEvent): Unit =
    applicationController.modalWindowController.showAndWait()
  // #actionShowModalWindow

}
