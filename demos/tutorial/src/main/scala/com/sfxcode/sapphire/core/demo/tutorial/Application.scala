package com.sfxcode.sapphire.core.demo.tutorial

import com.sfxcode.sapphire.core.cdi.FXApp
import javafx.scene.Scene
import javafx.stage.Stage

object Application extends FXApp {

  JFXApp.AutoShow = true

  override def applicationStage: Stage = {
    new PrimaryStage {
      title = "%s Tutorial (%s)".format(BuildInfo.name, BuildInfo.version)
      minHeight = 400
      minWidth = 600
      scene = new Scene {
      }
    }
  }
}

