package com.sfxcode.sapphire.core.cdi

import scalafx.stage.Stage
import javax.enterprise.util.AnnotationLiteral

import scalafx.application.JFXApp

import com.sfxcode.sapphire.core.cdi.annotation._
import com.sfxcode.sapphire.core.cdi.provider.ApplicationParametersProvider
import org.apache.deltaspike.core.api.provider.BeanProvider
import com.typesafe.config.ConfigFactory
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.shape.Rectangle

abstract class FXApp extends JFXApp {
  val configuration = ConfigFactory.load()

  init(applicationStage)

  def applicationStage: Stage = createPrimaryStage()

  def createPrimaryStage(stageWidth: Int = configuration.getInt("sapphire.core.defaultStage.width"),
                         stageHeight: Int = configuration.getInt("sapphire.core.defaultStage.height"),
                         stageTitle: String = configuration.getString("sapphire.core.defaultStage.title")):PrimaryStage = new PrimaryStage {
    title = stageTitle
    width = stageWidth
    height = stageHeight
    scene = new Scene {
      content = new Rectangle {
      }
    }
  }

  def init(primaryStage: Stage) {
    CDILauncher.init()
    val parameterProvider = BeanProvider.getContextualReference(classOf[ApplicationParametersProvider], false)
    parameterProvider.setParameters(parameters)

    applicationWillLaunch()

    val fxApp = BeanProvider.getContextualReference(classOf[ApplicationLauncher], false)
    fxApp.launch(primaryStage, startupLiteral)

  }

  def applicationWillLaunch() {}

  def applicationWillTerminate() {}

  def startupLiteral: AnnotationLiteral[_] = {
    new AnnotationLiteral[Startup] {}
  }

  override def stopApp(): Unit = {
    applicationWillTerminate()
    CDILauncher.shutdown()
  }
}







