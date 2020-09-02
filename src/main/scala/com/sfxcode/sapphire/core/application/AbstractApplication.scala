package com.sfxcode.sapphire.core.application

import com.sfxcode.sapphire.core.controller.AbstractApplicationController
import com.sfxcode.sapphire.core.stage.StageSupport
import com.typesafe.scalalogging.LazyLogging
import javafx.application.Application

// #FXApp
abstract class AbstractApplication extends StageSupport with LazyLogging {
  val startTime: Long = System.currentTimeMillis()

  def main(args: Array[String]): Unit = {
    ApplicationEnvironment.application = this
    Application.launch(classOf[FXApplication], args: _*)
  }

  val applicationController: AbstractApplicationController

  def applicationWillLaunch() {}

  def applicationDidLaunch(): Unit =
    logger.info("Application Startup in %s ms".format(System.currentTimeMillis() - startTime))

  def applicationWillTerminate() {}

}
// #FXApp
