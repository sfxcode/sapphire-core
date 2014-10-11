package com.sfxcode.sapphire.core.demo.appdemo

import com.sfxcode.sapphire.core.cdi.FXApp
import javax.inject.Named
import javax.enterprise.context.ApplicationScoped
import com.sfxcode.sapphire.core.controller.AppController
import com.sfxcode.sapphire.core.demo.appdemo.controller.MainWindowController
import scalafx.application.JFXApp
import scalafx.stage.Stage
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene

object DemoApplication extends FXApp {
  JFXApp.AUTO_SHOW = true
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




