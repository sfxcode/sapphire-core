package com.sfxcode.sapphire.core.application

import com.sfxcode.sapphire.core.cdi.annotation.Startup
import com.sfxcode.sapphire.core.cdi.{ApplicationLauncher, CDILauncher}
import javax.enterprise.util.AnnotationLiteral
import org.apache.deltaspike.core.api.provider.BeanProvider

class FXApplication extends javafx.application.Application {

  def start(stage: javafx.stage.Stage) {
    FXApp.Application = this
    val fxApp            = BeanProvider.getContextualReference(classOf[ApplicationLauncher], false)
    val applicationStage = FXApp.App.stage
    fxApp.launch(applicationStage, startupLiteral)
    applicationStage.show()
  }

  def startupLiteral: AnnotationLiteral[_] =
    new AnnotationLiteral[Startup] {}

  override def init(): Unit =
    CDILauncher.init()

  override def stop(): Unit = {
    FXApp.App.applicationWillTerminate()
    CDILauncher.shutdown()
  }
}
