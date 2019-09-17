package com.sfxcode.sapphire.core.demo.tutorial

import java.util.{Locale, ResourceBundle}

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
    // #Resources
    applicationEnvironment.loadResourceBundle("bundles/application")
    // #Resources
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

  // #CustomBundle
  // only example values ...
  override def resourceBundleForView(viewPath: String): ResourceBundle = {
    if (viewPath.contains("mySpecialViewName")) {
      val path = "myCustomResourcePath"
      val classLoader = Thread.currentThread().getContextClassLoader
      ResourceBundle.getBundle(path, Locale.getDefault(), classLoader)
    } else {
      super.resourceBundleForView(viewPath) // =  applicationEnvironment.resourceBundle
    }
  }

  // #CustomBundle

}

case class ApplicationName(name: String)
