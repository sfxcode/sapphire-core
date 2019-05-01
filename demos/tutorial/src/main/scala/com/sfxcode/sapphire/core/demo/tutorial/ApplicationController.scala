package com.sfxcode.sapphire.core.demo.tutorial

import javax.enterprise.context.ApplicationScoped
import javax.inject.Named

import com.sfxcode.sapphire.core.controller.AppController
import com.sfxcode.sapphire.core.demo.tutorial.controller.MainWindowController

@Named
@ApplicationScoped
class ApplicationController extends AppController {

  lazy val mainWindowController = getController[MainWindowController]()

  def applicationDidLaunch() {
    logger.info("start " + this)
    applicationEnvironment.loadResourceBundle("bundles/application")
    replaceSceneContent(mainWindowController)
  }
}
