package com.sfxcode.sapphire.core.application

import com.sfxcode.sapphire.core.stage.StageSupport
import javafx.application.Application
import javafx.stage.Stage

// #FXApp
trait FXApp extends StageSupport {

  override def createDefaultStage: Stage = {
    val result = super.createDefaultStage
    initStage(result)
    result
  }

  def initStage(stage: Stage): Unit = {}

  def main(args: Array[String]): Unit = {
    FXApp.App = this
    Application.launch(classOf[FXApplication], args: _*)
  }

  def applicationWillLaunch() {}

  def applicationWillTerminate() {}

}

object FXApp {
  var Application: Application = _
  var App: FXApp = _
}

// #FXApp
