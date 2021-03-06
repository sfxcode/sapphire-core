package com.sfxcode.sapphire.core.application

import java.util.{ Locale, ResourceBundle }

import com.sfxcode.sapphire.core.controller.BaseApplicationController
import com.sfxcode.sapphire.core.fxml.FxmlExpressionResolver
import com.sfxcode.sapphire.core.fxml.loader.{ BaseDocumentLoader, DocumentLoader }
import com.sfxcode.sapphire.core.scene.NodePropertyResolver
import com.typesafe.scalalogging.LazyLogging
import javafx.collections.{ FXCollections, ObservableMap }
import javafx.util.StringConverter
import javafx.util.converter.DefaultStringConverter

object ApplicationEnvironment extends Serializable with LazyLogging {

  private var app: BaseApplication = _
  private var appController: BaseApplicationController = _

  var documentLoader: BaseDocumentLoader = new DocumentLoader

  def setApplication(application: BaseApplication): Unit = app = application
  def setApplicationController(applicationController: BaseApplicationController): Unit =
    appController = applicationController

  var wrappedApplication: FXApplication = _

  def application[T <: BaseApplication](implicit e: T DefaultsTo BaseApplication): T = app.asInstanceOf[T]

  def applicationController[T <: BaseApplicationController](implicit e: T DefaultsTo BaseApplicationController): T =
    appController.asInstanceOf[T]

  var nodePropertyResolver: NodePropertyResolver = NodePropertyResolver()

  var fxmlExpressionResolver = new FxmlExpressionResolver[String, Any]

  var resourceBundle: ResourceBundle = _

  val converterMap: ObservableMap[String, StringConverter[_]] =
    FXCollections.observableHashMap[String, StringConverter[_]]()

  def loadResourceBundle(path: String): Unit = {
    val classLoader = Thread.currentThread().getContextClassLoader
    resourceBundle = ResourceBundle.getBundle(path, Locale.getDefault(), classLoader)
  }

  def clearResourceBundleCache(): Unit = {
    val classLoader = Thread.currentThread().getContextClassLoader
    ResourceBundle.clearCache(classLoader)
  }

  def getConverterByName[T](name: String, forceNew: Boolean = false): StringConverter[T] = {

    var className = name
    if (!name.contains("."))
      className = "javafx.util.converter." + guessConverterName(name)

    if (!forceNew && converterMap.containsKey(className))
      converterMap.get(className).asInstanceOf[StringConverter[T]]
    else {

      var result = new DefaultStringConverter().asInstanceOf[StringConverter[T]]

      try {
        val converterClass = Class.forName(className)
        val converter = converterClass.getDeclaredConstructor().newInstance()
        if (converter != null)
          result = converter.asInstanceOf[StringConverter[T]]
      } catch {
        case e: Exception =>
          logger.warn(e.getMessage)
          logger.warn("use default converter for name: " + className)
      }
      converterMap.put(className, result)
      result
    }

  }

  private def guessConverterName(className: String): String =
    if (!className.endsWith("StringConverter"))
      className + "StringConverter"
    else
      className
}
