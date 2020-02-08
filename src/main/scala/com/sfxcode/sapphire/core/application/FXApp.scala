package com.sfxcode.sapphire.core.application

import com.sfxcode.sapphire.core.stage.StageSupport
import javafx.application.Application
import javafx.stage.Stage

// #FXApp
trait FXApp extends StageSupport {

  def stage: Stage = createDefaultStage

  def main(args: Array[String]): Unit = {
    FXApp.App = this
    Application.launch(classOf[FXApplication], args: _*)
  }

  def applicationWillLaunch() {}

  def applicationWillTerminate() {}

}

object FXApp {
  var Application: Application = _
  var App: FXApp               = _
}

// #FXApp
