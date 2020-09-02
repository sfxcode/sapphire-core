package com.sfxcode.sapphire.core.showcase

import com.sfxcode.sapphire.core.application.AbstractApplication
import com.sfxcode.sapphire.core.controller.AbstractApplicationController
import com.sfxcode.sapphire.core.showcase.controller.ShowcaseViewController
import com.typesafe.scalalogging.LazyLogging

object Application extends AbstractApplication {

  override def title: String = "Sapphire Core Showcase"

  override val applicationController: AbstractApplicationController = new ApplicationController

}

class ApplicationController extends AbstractApplicationController with LazyLogging {

  lazy val showcaseController: ShowcaseViewController = getController[ShowcaseViewController]()

  def applicationDidLaunch() {
    logger.debug("start " + this)
    replaceSceneContent(showcaseController)
  }
}
