package com.sfxcode.sapphire.core.controller

import com.sfxcode.sapphire.core.application.ApplicationEnvironment
import com.sun.javafx.css.StyleManager
import javafx.application.Platform
import javafx.stage.Stage

abstract class DefaultWindowController extends WindowController {

  override def isMainWindow: Boolean = true

  def onApplicationStartup(stage: Stage) {
    applicationWillLaunch()
    setStage(stage)
    ApplicationEnvironment.defaultWindowController = this
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
