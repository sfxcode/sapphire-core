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

  def applicationController = windowController.get.asInstanceOf[ApplicationController]

  def actionShowSecondWindow(event: ActionEvent): Unit = {
    val x = applicationController.stage.getX + applicationController.stage.getWidth
    val y = applicationController.stage.getY
    applicationController.secondWindowController.show(x, y)
  }

  def actionCloseSecondWindow(event: ActionEvent): Unit =
    applicationController.secondWindowController.close()

  def actionShowThirdWindow(event: ActionEvent): Unit = {
    val x = applicationController.stage.getX - applicationController.thirdWindowController.stage.getWidth
    val y = applicationController.stage.getY
    applicationController.thirdWindowController.show(x, y)
  }

  def actionCloseThirdWindow(event: ActionEvent): Unit =
    applicationController.thirdWindowController.close()
}
