package com.sfxcode.sapphire.core.controller

import com.sfxcode.sapphire.core.stage.StageSupport

abstract class AdditionalWindowController extends WindowController with StageSupport {

  override def isMainWindow: Boolean = false

  def initStage(): Unit =
    if (stageProperty.isEmpty) {
      setStage(createDefaultStage)
    }

  def show(x: Double = 0.0, y: Double = 0.0): Unit = stageProperty.foreach { stage =>
    if (!stage.isShowing) {
      if (x > 0) {
        stage.setX(x)
      }
      if (y > 0) {
        stage.setY(y)
      }
      stage.show()
    }
  }

  def close(): Unit = stageProperty.foreach { stage =>
    if (stage.isShowing) {
      stage.close()

    }
  }

}
