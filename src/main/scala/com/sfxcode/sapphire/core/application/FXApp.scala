package com.sfxcode.sapphire.core.application

import com.sfxcode.sapphire.core.ConfigValues
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.HBox
import javafx.stage.Stage

trait FXApp extends ConfigValues {

  def stage: Stage = createDefaultStage

  def createDefaultStage: Stage = {
    val stage = new Stage()
    stage.setWidth(width)
    stage.setHeight(height)
    if (forceMinWidth)
      stage.setMinWidth(width)
    if (forceMinHeight)
      stage.setMinHeight(height)
    if (forceMaxWidth)
      stage.setMaxWidth(width)
    if (forceMaxHeight)
      stage.setMaxHeight(height)
    stage.setTitle(title)
    initStage(stage)
    val scene = new Scene(new HBox())
    stage.setScene(scene)
    stage
  }

  def initStage(stage: Stage): Unit = {}

  def title: String = configStringValue("sapphire.core.defaultStage.title", "SFX Application")

  def width: Int = configIntValue("sapphire.core.defaultStage.width", 800)

  def height: Int = configIntValue("sapphire.core.defaultStage.height", 600)

  def forceMinWidth: Boolean = true

  def forceMinHeight: Boolean = true

  def forceMaxWidth: Boolean = false

  def forceMaxHeight: Boolean = false

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
