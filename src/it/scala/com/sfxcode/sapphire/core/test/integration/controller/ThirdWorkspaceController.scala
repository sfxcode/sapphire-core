package com.sfxcode.sapphire.core.test.integration.controller

import javafx.fxml.FXML

import javafx.scene.layout.StackPane
import scalafx.Includes._

class ThirdWorkspaceController extends AbstractWorkspaceController {

  @FXML
  var infoPane: StackPane = _

  lazy val infoController: DisplayInfoController = getController[DisplayInfoController]()

  override def didGainVisibilityFirstTime(): Unit = {
    super.didGainVisibilityFirstTime()
    updatePaneContent(infoPane, infoController)

  }
}
