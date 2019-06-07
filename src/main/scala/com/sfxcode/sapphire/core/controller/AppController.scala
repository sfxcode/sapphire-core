package com.sfxcode.sapphire.core.controller

import com.sfxcode.sapphire.core.cdi.annotation.{FXStage, Startup}
import com.sun.javafx.css.StyleManager
import javax.enterprise.event.Observes
import scalafx.application.Platform
import scalafx.scene.Scene
import scalafx.stage.Stage

abstract class AppController extends MainWindowController {

  def startup(@Observes @FXStage @Startup stage: Stage) {
    applicationStartup(stage)
  }

  def applicationStartup(stage: Stage) {
    setStage(stage)
    applicationEnvironment.applicationController = this
    applicationDidLaunch()
  }

  def applicationDidLaunch()

  def applicationWillStop(): Unit = {
    logger.debug("exit in Progress")
  }

  def reloadStyles(): Unit = {
    StyleManager.getInstance().stylesheetContainerMap.clear()
  }

  def exit(): Unit = {
    applicationWillStop()
    Platform.exit()
  }

}
