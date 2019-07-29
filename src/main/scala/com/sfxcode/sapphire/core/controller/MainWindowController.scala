package com.sfxcode.sapphire.core.controller

import javafx.stage.Stage

abstract class MainWindowController extends WindowController {

  override def isMainWindow: Boolean = true

  override def setStage(stage: Stage): Unit = {
    stageProperty.set(stage)
    sceneProperty.set(stage.getScene)
  }

}
