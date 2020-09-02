package com.sfxcode.sapphire.core.application

import javafx.stage.Stage

class FXApplication extends javafx.application.Application {
  var applicationStage: Stage = _

  def start(stage: javafx.stage.Stage) {
    FXApp.Application = this
    val applicationStage = FXApp.App.createDefaultStage
    FXApp.App.applicationWillLaunch()
    FXApp.App.applicationController.onApplicationStartup(applicationStage)
    applicationStage.show()
  }

  override def stop(): Unit =
    FXApp.App.applicationWillTerminate()
}
