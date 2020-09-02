package com.sfxcode.sapphire.core.demo.tutorial

import java.util.{ Locale, ResourceBundle }

import com.sfxcode.sapphire.core.application.ApplicationEnvironment
import com.sfxcode.sapphire.core.controller.DefaultWindowController
import com.sfxcode.sapphire.core.demo.tutorial.controller.MainViewController

class ApplicationController extends DefaultWindowController {

  lazy val mainViewController: MainViewController =
    getController[MainViewController]()

  def applicationDidLaunch() {
    logger.info("start " + this)
    // #Resources
    ApplicationEnvironment.loadResourceBundle("bundles/application")
    // #Resources
    replaceSceneContent(mainViewController)
  }

  def reload(): Unit = {
    // Styling
    reloadStyles()
    // Resources
    ApplicationEnvironment.clearResourceBundleCache()
    ApplicationEnvironment.loadResourceBundle("bundles/application")
    // FXML
    val newMainViewController = getController[MainViewController]()
    replaceSceneContent(newMainViewController)
  }

  def applicationName: ApplicationName =
    ApplicationName(configStringValue("application.name"))

  // #CustomBundle
  // only example values ...
  override def resourceBundleForView(viewPath: String): ResourceBundle =
    if (viewPath.contains("mySpecialViewName")) {
      val path = "myCustomResourcePath"
      val classLoader = Thread.currentThread().getContextClassLoader
      ResourceBundle.getBundle(path, Locale.getDefault(), classLoader)
    } else
      super.resourceBundleForView(viewPath) // =  applicationEnvironment.resourceBundle

  // #CustomBundle

}

case class ApplicationName(name: String)
