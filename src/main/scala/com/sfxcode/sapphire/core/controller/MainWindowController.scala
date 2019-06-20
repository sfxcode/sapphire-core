package com.sfxcode.sapphire.core.controller

import scalafx.scene.Scene
import scalafx.stage.Stage

abstract class MainWindowController extends WindowController {

  override def isMainWindow: Boolean = true

  override def setStage(stage: Stage): Unit = {
    stageProperty.set(stage)
    sceneProperty.set(new Scene(stage.getScene))
  }

}
