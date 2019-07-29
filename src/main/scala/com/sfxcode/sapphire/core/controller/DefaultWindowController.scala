package com.sfxcode.sapphire.core.controller

import com.sfxcode.sapphire.core.cdi.annotation.{FXStage, Startup}
import com.sun.javafx.css.StyleManager
import javafx.application.Platform
import javafx.stage.Stage
import javax.annotation.PreDestroy
import javax.enterprise.event.Observes

abstract class DefaultWindowController extends WindowController {

  override def isMainWindow: Boolean = true

  def startup(@Observes @FXStage @Startup stage: Stage) {
    applicationStartup(stage)
  }

  def applicationStartup(stage: Stage) {
    setStage(stage)
    applicationEnvironment.defaultWindowController = this
    applicationDidLaunch()
  }

  def applicationDidLaunch()

  def applicationWillStop(): Unit = {
    logger.debug("exit in Progress")
  }

  def reloadStyles(): Unit = {
    StyleManager.getInstance().stylesheetContainerMap.clear()
  }

  @PreDestroy
  def preDestroy(): Unit = shutdown()

  def shutdown(): Unit = {
    applicationWillStop()
  }

  def exit(): Unit = {
    Platform.exit()
  }

}
