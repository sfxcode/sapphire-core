package com.sfxcode.sapphire.core.demo.base

import javax.enterprise.util.AnnotationLiteral
import com.sfxcode.sapphire.core.cdi.FXApp
import javax.inject.Named
import javax.enterprise.context.ApplicationScoped
import com.sfxcode.sapphire.core.controller.AppController
import javax.enterprise.event.Observes
import com.sfxcode.sapphire.core.cdi.annotation.FXStage
import scalafx.stage.Stage
import com.sfxcode.sapphire.core.demo.base.controller.MainWindowController
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene


object Application extends FXApp {

  override def applicationStage:Stage  = new PrimaryStage {
    title = "Sapphire Controller Demo"
    scene = new Scene {

    }
  }
  override def startupLiteral: AnnotationLiteral[_] = new AnnotationLiteral[BaseStartup] {}
}

@Named
@ApplicationScoped
class BaseApplicationController extends AppController {

  lazy val mainWindowController = getController[MainWindowController]()

  def applicationDidLaunch() {
    println("start " + this)
    replaceSceneContent(mainWindowController)
  }

  override def startup(@Observes @FXStage @BaseStartup stage: Stage) {
    applicationStartup(stage)
  }

}




