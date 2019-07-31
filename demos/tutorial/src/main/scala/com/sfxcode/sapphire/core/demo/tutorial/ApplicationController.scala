package com.sfxcode.sapphire.core.demo.tutorial

import com.sfxcode.sapphire.core.controller.DefaultWindowController
import com.sfxcode.sapphire.core.demo.tutorial.controller.MainController
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces
import javax.inject.Named

@Named
@ApplicationScoped
class ApplicationController extends DefaultWindowController {

  lazy val mainWindowController: MainController =
    getController[MainController]()

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
    val newMainWindowController = getController[MainController]()
    replaceSceneContent(newMainWindowController)
  }

  @Produces
  def applicationName: ApplicationName = {
    ApplicationName(configStringValue("application.name"))
  }
}

case class ApplicationName(name: String)
