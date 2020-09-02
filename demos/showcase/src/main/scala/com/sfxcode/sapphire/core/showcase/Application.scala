package com.sfxcode.sapphire.core.showcase

import com.sfxcode.sapphire.core.application.FXApp
import com.sfxcode.sapphire.core.controller.DefaultWindowController
import com.sfxcode.sapphire.core.showcase.controller.ShowcaseViewController
import com.typesafe.scalalogging.LazyLogging

object Application extends FXApp {
  override def title: String = "Sapphire Core Showcase"

  override val applicationController: DefaultWindowController = new ApplicationController
}

class ApplicationController extends DefaultWindowController with LazyLogging {

  lazy val showcaseController: ShowcaseViewController = getController[ShowcaseViewController]()

  def applicationDidLaunch() {
    logger.debug("start " + this)
    replaceSceneContent(showcaseController)
  }
}
