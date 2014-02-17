package com.sfxcode.sapphire.core.demo.issues

import javax.inject.Named
import javax.enterprise.context.ApplicationScoped
import com.sfxcode.sapphire.core.controller.AppController
import scalafx.stage.Stage
import com.sfxcode.sapphire.core.cdi.FXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.paint.Color._
import com.sfxcode.sapphire.core.demo.login.mdel.User
import javax.enterprise.util.AnnotationLiteral
import javax.enterprise.event.Observes
import com.sfxcode.sapphire.core.cdi.annotation.FXStage
import com.sfxcode.sapphire.core.demo.issues.controller.IssueTrackingLiteController

object Application extends FXApp {

  override def applicationStage:Stage  = new PrimaryStage {
    title = "Sapphire - Issue Tracking Lite Sample"
    minWidth = 390
    minHeight = 500
    width = 800
    height = 600
    scene = new Scene {
      scene = new Scene {
        fill = LIGHTBLUE
      }
    }
  }

  override def startupLiteral: AnnotationLiteral[_] = new AnnotationLiteral[IssuesStartup] {}


}

@Named
@ApplicationScoped
class IssuesApplicationController extends AppController {
  lazy val mainController = getController[IssueTrackingLiteController]()


  override def startup(@Observes @FXStage @IssuesStartup stage: Stage) {
    applicationStartup(stage)
  }

  def applicationDidLaunch() {
    println("start " + this)
    replaceSceneContent(mainController)

  }


}
