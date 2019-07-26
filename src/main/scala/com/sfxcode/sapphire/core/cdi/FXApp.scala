package com.sfxcode.sapphire.core.cdi

import com.sfxcode.sapphire.core.base.ConfigValues
import com.sfxcode.sapphire.core.cdi.annotation._
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.HBox
import javafx.stage.Stage
import javax.enterprise.util.AnnotationLiteral
import org.apache.deltaspike.core.api.provider.BeanProvider

class FXApp extends Application with ConfigValues {

  //init(applicationStage)

  def applicationStage: Stage = createPrimaryStage

  def createPrimaryStage: Stage = {
    val result = new Stage()
    result.setWidth(configIntValue("sapphire.core.defaultStage.width"))
    result.setHeight(configIntValue("sapphire.core.defaultStage.height"))
    val region = new HBox()
    val scene = new Scene(region)
    result.setScene(scene)


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

  override def init {
    //val parameterProvider = BeanProvider.getContextualReference(classOf[ApplicationParametersProvider], false)
    // TODO
    //parameterProvider.setParameters(parameters)

    applicationWillLaunch()

  }

  def applicationWillLaunch() {}

  def applicationWillTerminate() {}

  def startupLiteral: AnnotationLiteral[_] = {
    new AnnotationLiteral[Startup] {}
  }

  override def stop(): Unit = {
    applicationWillTerminate()
    CDILauncher.shutdown()
  }

  override def start(stage: Stage): Unit = {
    CDILauncher.init()
    ////
    val fxApp = BeanProvider.getContextualReference(classOf[ApplicationLauncher], false)
    fxApp.launch(stage, startupLiteral)
    stage.show()

  }
}

