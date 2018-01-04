package com.sfxcode.sapphire.core.demo.appdemo.controller

import javafx.fxml.FXML

import javafx.scene.layout.StackPane
import scalafx.Includes._

class Workspace3Controller extends AbstractWorkspaceController {

  @FXML
  var infoPane: StackPane = _

  lazy val infoController = getController[DisplayInfoController]()

  override def didGainVisibilityFirstTime(): Unit = {
    super.didGainVisibilityFirstTime()
    updatePaneContent(infoPane, infoController)

  }
}
