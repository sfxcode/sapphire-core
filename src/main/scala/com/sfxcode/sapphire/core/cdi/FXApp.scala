package com.sfxcode.sapphire.core.cdi

import com.sfxcode.sapphire.core.base.ConfigValues
import javax.enterprise.util.AnnotationLiteral
import com.sfxcode.sapphire.core.cdi.annotation._
import com.sfxcode.sapphire.core.cdi.provider.ApplicationParametersProvider
import org.apache.deltaspike.core.api.provider.BeanProvider
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.shape.Rectangle
import scalafx.stage.Stage

abstract class FXApp extends JFXApp with ConfigValues {

  init(applicationStage)

  def applicationStage: Stage = createPrimaryStage()

  def createPrimaryStage(
    stageWidth: Int = configIntValue("sapphire.core.defaultStage.width"),
    stageHeight: Int = configIntValue("sapphire.core.defaultStage.height"),
    stageTitle: String = configStringValue("sapphire.core.defaultStage.title")): PrimaryStage = new PrimaryStage {
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

