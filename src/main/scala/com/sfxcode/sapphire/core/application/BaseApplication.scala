package com.sfxcode.sapphire.core.application

import com.sfxcode.sapphire.core.controller.BaseApplicationController
import com.sfxcode.sapphire.core.stage.StageSupport
import com.typesafe.scalalogging.LazyLogging
import javafx.application.Application

// #BaseApplication
abstract class BaseApplication extends StageSupport with LazyLogging {
  val startTime: Long = System.currentTimeMillis()

  def main(args: Array[String]): Unit = {
    ApplicationEnvironment.setApplication(this)
    Application.launch(classOf[FXApplication], args: _*)
  }

  val applicationController: BaseApplicationController

  def applicationWillLaunch() {}

  def applicationDidLaunch(): Unit =
    logger.info("Application Startup in %s ms".format(System.currentTimeMillis() - startTime))

  def applicationWillTerminate() {}

}
// #BaseApplication
