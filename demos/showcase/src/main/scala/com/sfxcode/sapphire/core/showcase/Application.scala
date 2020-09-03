package com.sfxcode.sapphire.core.showcase

import com.sfxcode.sapphire.core.application.BaseApplication
import com.sfxcode.sapphire.core.controller.BaseApplicationController
import com.sfxcode.sapphire.core.showcase.controller.ShowcaseViewController
import com.typesafe.scalalogging.LazyLogging

object Application extends BaseApplication {

  override def title: String = "Sapphire Core Showcase"

  override val applicationController: BaseApplicationController = new ApplicationController

}

class ApplicationController extends BaseApplicationController with LazyLogging {

  lazy val showcaseController: ShowcaseViewController = getController[ShowcaseViewController]()

  def applicationDidLaunch() {
    logger.debug("start " + this)
    replaceSceneContent(showcaseController)
  }
}
