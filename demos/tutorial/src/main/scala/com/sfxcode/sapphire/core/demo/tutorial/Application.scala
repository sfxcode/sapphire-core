package com.sfxcode.sapphire.core.demo.tutorial

import com.sfxcode.sapphire.core.BuildInfo
import com.sfxcode.sapphire.core.cdi.FXApp
import com.typesafe.config.ConfigFactory
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.stage.Stage

object Application extends FXApp {

  JFXApp.AutoShow = true

  override def applicationStage: Stage = {
    val conf = ConfigFactory.load()
    new PrimaryStage {
      title = "%s Tutorial (%s)".format(BuildInfo.name, BuildInfo.version)
      minHeight = 400
      minWidth = 600
      scene = new Scene {
      }
    }
  }
}

