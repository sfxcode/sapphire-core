package com.sfxcode.sapphire.core.demo.tutorial

import javax.enterprise.context.ApplicationScoped
import javax.inject.Named
import com.sfxcode.sapphire.core.controller.AppController
import com.sfxcode.sapphire.core.demo.tutorial.controller.MainWindowController
import javax.enterprise.inject.Produces

@Named
@ApplicationScoped
class ApplicationController extends AppController {

  lazy val mainWindowController: MainWindowController =
    getController[MainWindowController]()

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
    val newMainWindowController = getController[MainWindowController]()
    replaceSceneContent(newMainWindowController)
  }

  @Produces
  def applicationName: ApplicationName = {
    ApplicationName(configStringValue("application.name"))
  }
}

case class ApplicationName(name: String)
