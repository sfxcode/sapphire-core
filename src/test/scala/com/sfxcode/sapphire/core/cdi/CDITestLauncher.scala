package com.sfxcode.sapphire.core.cdi

import scalafx.application.JFXApp.PrimaryStage
import scalafx.stage.Stage
import scalafx.scene.Scene
import scalafx.scene.layout.BorderPane
import scalafx.geometry.Insets
import scalafx.scene.control.Label

object CDITestLauncher extends  FXApp{

  override def applicationStage:Stage  = new PrimaryStage {
    title = "Sapphire Controller Demo"
    scene = new Scene {
      root = new BorderPane {
        padding = Insets(25)
        center = new Label("Hello Sapphire")
      }
    }
  }
}
