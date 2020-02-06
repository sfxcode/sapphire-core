package com.sfxcode.sapphire.core.demo.windows.controller

import com.sfxcode.sapphire.core.controller.ViewController
import com.typesafe.scalalogging.LazyLogging
import javafx.fxml.FXML
import javafx.scene.control.Label

class AdditionalViewController extends ViewController with LazyLogging {

  @FXML
  var windowLabel: Label = _

  override def startup() {
    logger.debug("class: " + this)
  }

  override def didGainVisibilityFirstTime() {
    super.didGainVisibility()
    windowLabel.setText(windowController.get.name)
  }
}
