package com.sfxcode.sapphire.core.controller

import com.sfxcode.sapphire.core.cdi.annotation.{FXStage, Startup}
import com.sun.javafx.css.StyleManager
import javafx.application.Platform
import javafx.stage.Stage
import javax.enterprise.event.Observes

abstract class DefaultWindowController extends WindowController {

  override def isMainWindow: Boolean = true

  def startup(@Observes @FXStage @Startup stage: Stage) {
    applicationStartup(stage)
  }

  def applicationStartup(stage: Stage) {
    applicationWillLaunch()
    setStage(stage)
    applicationEnvironment.defaultWindowController = this
    applicationDidLaunch()
  }

  def applicationWillLaunch() {}

  def applicationDidLaunch()

  def reloadStyles(): Unit =
    StyleManager.getInstance().stylesheetContainerMap.clear()

  override def shutdown(): Unit = {
    super.shutdown()
    applicationWillStop()
  }

  def applicationWillStop(): Unit =
    logger.debug("exit in Progress")

  def exit(): Unit =
    Platform.exit()

}
