package com.sfxcode.sapphire.core.demo.windows.controller

import com.sfxcode.sapphire.core.controller.ViewController
import com.sfxcode.sapphire.core.demo.windows.ApplicationController
import com.sfxcode.sapphire.core.value.FXBean
import com.typesafe.scalalogging.LazyLogging
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Label

import scala.beans.BeanProperty

class MainViewController extends ViewController with LazyLogging {

  @FXML
  var windowLabel: Label = _

  @FXML
  var controllerLabel: Label = _

  @BeanProperty
  var name: String = "MainView"

  @BeanProperty
  var bean: FXBean[ViewController] = FXBean(this)

  override def startup() {
    logger.debug("class: " + this)
  }

  override def didGainVisibilityFirstTime() {
    super.didGainVisibilityFirstTime()
    val expressionResult =
      evaluateExpressionOnObject[String](this, "result: [${_self.windowLabel().getText()}]").getOrElse("")
    controllerLabel.setText(expressionResult)
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
