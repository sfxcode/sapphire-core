package com.sfxcode.sapphire.core.showcase

import com.sfxcode.sapphire.core.application.FXApp
import com.sfxcode.sapphire.core.controller.DefaultWindowController
import com.sfxcode.sapphire.core.showcase.controller.ShowcaseViewController
import com.typesafe.scalalogging.LazyLogging
import javax.enterprise.context.ApplicationScoped
import javax.inject.Named

//object Application extends FXApp {
//  override def title: String = "Sapphire Core Showcase"
//}

@Named
@ApplicationScoped
class ApplicationController extends DefaultWindowController with LazyLogging {

  lazy val showcaseController: ShowcaseViewController = getController[ShowcaseViewController]()

  def applicationDidLaunch() {
    logger.debug("start " + this)
    replaceSceneContent(showcaseController)
  }
}
