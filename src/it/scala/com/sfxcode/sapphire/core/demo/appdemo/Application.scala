package com.sfxcode.sapphire.core.demo.appdemo

// #application

import com.sfxcode.sapphire.core.cdi.FXApp

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.stage.Stage

object DemoApplication extends FXApp {
  JFXApp.AutoShow = true

  override def applicationStage: Stage = new PrimaryStage {
    title = "Sapphire Controller Demo"
    scene = new Scene {
      minHeight = 600
      minWidth = 800
    }
  }
}
// #application

// #applicationController

import com.sfxcode.sapphire.core.controller.AppController
import com.sfxcode.sapphire.core.demo.appdemo.controller.MainWindowController

import javax.enterprise.context.ApplicationScoped
import javax.inject.Named

@Named
@ApplicationScoped
class ApplicationController extends AppController {

  def mainWindowController: MainWindowController = getController[MainWindowController]()

  def applicationDidLaunch() {
    logger.info("start " + this)
    reload()
  }

  // live reloading of scene content for rapid development
  def reload(): Unit = {
    applicationEnvironment.loadResourceBundle("com/sfxcode/sapphire/core/demo/appdemo/bundles/demo")
    replaceSceneContent(mainWindowController)
  }

}

// #applicationController
