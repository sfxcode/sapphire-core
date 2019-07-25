package com.sfxcode.sapphire.core.cdi

import com.sfxcode.sapphire.core.base.ConfigValues
import com.sfxcode.sapphire.core.cdi.annotation._
import com.sfxcode.sapphire.core.cdi.provider.ApplicationParametersProvider
import javafx.stage.Stage
import javax.enterprise.util.AnnotationLiteral
import org.apache.deltaspike.core.api.provider.BeanProvider

abstract class FXApp extends ConfigValues {

  init(applicationStage)

  def applicationStage: Stage = createPrimaryStage

  def createPrimaryStage: Stage = {
    val result = new Stage()
    result.setWidth(configIntValue("sapphire.core.defaultStage.width"))

    //    stageWidth: Int
    //  } = configIntValue("sapphire.core.defaultStage.width"),
    //    stageHeight: Int = configIntValue("sapphire.core.defaultStage.height"),
    //    stageTitle: String = configStringValue("sapphire.core.defaultStage.title")): PrimaryStage = new PrimaryStage {
    //    title = stageTitle
    //    width = stageWidth
    //    height = stageHeight
    //    scene = new Scene {
    //      content = new Rectangle {
    //      }
    //    }
    result
  }

  def init(primaryStage: Stage) {
    CDILauncher.init()
    val parameterProvider = BeanProvider.getContextualReference(classOf[ApplicationParametersProvider], false)
    // TODO
    //parameterProvider.setParameters(parameters)

    applicationWillLaunch()

    val fxApp = BeanProvider.getContextualReference(classOf[ApplicationLauncher], false)
    fxApp.launch(primaryStage, startupLiteral)
  }

  def applicationWillLaunch() {}

  def applicationWillTerminate() {}

  def startupLiteral: AnnotationLiteral[_] = {
    new AnnotationLiteral[Startup] {}
  }

  def stopApp(): Unit = {
    applicationWillTerminate()
    CDILauncher.shutdown()
  }
}

