package com.sfxcode.sapphire.core.demo.tutorial

import com.sfxcode.sapphire.core.controller.DefaultWindowController
import com.sfxcode.sapphire.core.demo.tutorial.controller.MainViewController
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces
import javax.inject.Named

@Named
@ApplicationScoped
class ApplicationController extends DefaultWindowController {

  lazy val mainWindowController: MainViewController =
    getController[MainViewController]()

  def applicationDidLaunch() {
    logger.info("start " + this)
    applicationEnvironment.loadResourceBundle("bundles/application")
    replaceSceneContent(mainWindowController)
  }

  def reload(): Unit = {
    // Styling
    reloadStyles()
    // Resources
    applicationEnvironment.clearResourceBundleCache()
    applicationEnvironment.loadResourceBundle("bundles/application")
    // FXML
    val newMainWindowController = getController[MainViewController]()
    replaceSceneContent(newMainWindowController)
  }

  @Produces
  def applicationName: ApplicationName = {
    ApplicationName(configStringValue("application.name"))
  }
}

case class ApplicationName(name: String)
