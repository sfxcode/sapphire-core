package com.sfxcode.sapphire.core.application

import com.sfxcode.sapphire.core.base.ConfigValues
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.HBox
import javafx.stage.Stage

trait FXApp extends ConfigValues {

  def stage: Stage

  def main(args: Array[String]): Unit = {
    FXApp.App = this
    Application.launch(classOf[FXApplication], args: _*)
  }

  def applicationWillLaunch() {}

  def applicationWillTerminate() {}

  def createDefaultStage: Stage = {
    val result = new Stage()
    result.setMinWidth(configIntValue("sapphire.core.defaultStage.width", 800))
    result.setMinHeight(configIntValue("sapphire.core.defaultStage.height", 600))
    val scene = new Scene(new HBox())
    result.setScene(scene)
    result
  }

}

object FXApp {
  var Application: Application = _
  var App: FXApp = _
}
