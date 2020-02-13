package com.sfxcode.sapphire.core.stage

import com.sfxcode.sapphire.core.ConfigValues
import javafx.scene.Scene
import javafx.scene.layout.HBox
import javafx.stage.{ Modality, Stage, StageStyle }

trait StageSupport extends ConfigValues {

  def createDefaultStage: Stage = {
    val stage = new Stage()
    stage.setWidth(width)
    stage.setHeight(height)
    if (forceMinWidth) {
      stage.setMinWidth(width)
    }
    if (forceMinHeight) {
      stage.setMinHeight(height)
    }
    if (forceMaxWidth) {
      stage.setMaxWidth(width)
    }
    if (forceMaxHeight) {
      stage.setMaxHeight(height)
    }
    stage.setTitle(title)
    val scene = new Scene(new HBox())
    stage.setScene(scene)
    stage.initModality(modality)
    stage.initStyle(stageStyle)
    stage
  }

  def stageStyle: StageStyle = StageStyle.DECORATED

  def modality: Modality = Modality.NONE

  def title: String = configStringValue("sapphire.core.defaultStage.title", "SFX Application")

  def width: Int = configIntValue("sapphire.core.defaultStage.width", 800)

  def height: Int = configIntValue("sapphire.core.defaultStage.height", 600)

  def forceMinWidth: Boolean = true

  def forceMinHeight: Boolean = true

  def forceMaxWidth: Boolean = false

  def forceMaxHeight: Boolean = false

}
