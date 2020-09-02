package com.sfxcode.sapphire.core.application

import javafx.stage.Stage

class FXApplication extends javafx.application.Application {
  var applicationStage: Stage = _

  def start(stage: javafx.stage.Stage) {
    ApplicationEnvironment.wrappedApplication = this
    val application = ApplicationEnvironment.application
    applicationStage = application.createDefaultStage()
    application.applicationWillLaunch()

    val windowController = application.applicationController
    windowController.onApplicationStartup(applicationStage)

    application.applicationDidLaunch()

    applicationStage.show()
  }

  override def stop(): Unit =
    ApplicationEnvironment.application.applicationWillTerminate()
}
