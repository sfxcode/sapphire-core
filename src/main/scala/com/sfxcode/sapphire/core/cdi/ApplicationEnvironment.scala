package com.sfxcode.sapphire.core.cdi

import java.util.{ Locale, ResourceBundle }

import com.sfxcode.sapphire.core.controller.DefaultWindowController
import com.sfxcode.sapphire.core.fxml.FxmlExpressionResolver
import com.sfxcode.sapphire.core.scene.NodePropertyResolver
import javax.enterprise.context.ApplicationScoped
import javax.inject.Named

@Named
@ApplicationScoped
class ApplicationEnvironment extends Serializable {

  var nodePropertyResolver = NodePropertyResolver()

  var fxmlExpressionResolver = new FxmlExpressionResolver[String, Any]

  var defaultWindowController: DefaultWindowController = _

  var resourceBundle: ResourceBundle = _

  def loadResourceBundle(path: String): Unit = {
    val classLoader = Thread.currentThread().getContextClassLoader
    resourceBundle = ResourceBundle.getBundle(path, Locale.getDefault(), classLoader)
  }

  def clearResourceBundleCache(): Unit = {
    val classLoader = Thread.currentThread().getContextClassLoader
    ResourceBundle.clearCache(classLoader)
  }
}
