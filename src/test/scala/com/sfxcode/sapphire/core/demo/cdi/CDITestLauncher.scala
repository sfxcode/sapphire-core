package com.sfxcode.sapphire.core.demo.cdi

import com.sfxcode.sapphire.core.cdi.FXApp

import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout.BorderPane
import scalafx.stage.Stage

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
