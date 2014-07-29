package com.sfxcode.sapphire.core.demo.test

import com.sfxcode.sapphire.core.cdi.FXApp
import javax.inject.Named
import javax.enterprise.context.ApplicationScoped
import com.sfxcode.sapphire.core.controller.AppController
import scalafx.application.JFXApp
import scalafx.stage.Stage
import com.sfxcode.sapphire.core.demo.test.controller.MainWindowController
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene

object Application extends FXApp {
  JFXApp.AUTO_SHOW = false
  override def applicationStage:Stage  = new PrimaryStage {
    title = "Sapphire Controller Demo"
    scene = new Scene {

    }
  }
}

@Named
@ApplicationScoped
class BaseApplicationController extends AppController {

  lazy val mainWindowController = getController[MainWindowController]()

  def applicationDidLaunch() {
    logger.info("start " + this)
    replaceSceneContent(mainWindowController)
  }

}




