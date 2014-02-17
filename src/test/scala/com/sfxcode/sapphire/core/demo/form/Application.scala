package com.sfxcode.sapphire.core.demo.form

import javax.enterprise.util.AnnotationLiteral
import com.sfxcode.sapphire.core.cdi.FXApp
import javax.inject.Named
import javax.enterprise.context.ApplicationScoped
import com.sfxcode.sapphire.core.controller.AppController
import javax.enterprise.event.Observes
import com.sfxcode.sapphire.core.cdi.annotation.FXStage
import scalafx.stage.Stage
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import com.sfxcode.sapphire.core.demo.form.controller.FormController


object Application extends FXApp {

  override def applicationStage:Stage  = new PrimaryStage {
    title = "Sapphire Form Demo"
    scene = new Scene {

    }
  }
  override def startupLiteral: AnnotationLiteral[_] = new AnnotationLiteral[FormStartup] {}
}

@Named
@ApplicationScoped
class FormApplicationController extends AppController {

  lazy val formController = getController[FormController]()

  def applicationDidLaunch() {
    println("start " + this)
    replaceSceneContent(formController)
  }

  override def startup(@Observes @FXStage @FormStartup stage: Stage) {
    applicationStartup(stage)
  }

}




