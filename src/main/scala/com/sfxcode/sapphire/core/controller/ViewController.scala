package com.sfxcode.sapphire.core.controller

import java.net.URL
import java.util.ResourceBundle
import javafx.fxml.Initializable
import javax.annotation.{PostConstruct, PreDestroy}
import javax.inject.Inject

import com.sfxcode.sapphire.core.cdi.BeanResolver
import com.sfxcode.sapphire.core.fxml.FxmlLoading
import com.typesafe.config.Config

import scala.reflect.ClassTag

abstract class ViewController extends FxmlLoading with BeanResolver with ActionEvents with Initializable {

  implicit def stringListToMap(list: List[String]): Map[String, String] = list.map(s => (s, s)).toMap

  @Inject
  var configuration: Config = _

  var parent: ViewController = _

  var gainVisibility = false

  // bean lifecycle

  @PostConstruct
  def postConstruct() = startup()

  def startup() {}

  @PreDestroy
  def preDestroy() = shutdown()

  def shutdown() {}

  override def initialize(loc: URL, res: ResourceBundle): Unit = {
    location = loc
    resources = res
    didInitialize()
  }

  // controller lifecycle

  def didInitialize() {}

  def willGainVisibility() {}

  def didGainVisibility() {}

  def didGainVisibilityFirstTime() {}

  def willLooseVisibility() {}

  def didLooseVisibility() {}

  def stateMap = Map[String, Any]()

  def updateFromStateMap(map: Map[String, Any]): Unit = {}

  def actualSceneController: ViewController = applicationEnvironment.actualSceneController

  def isActualSceneController: Boolean = this == actualSceneController

  def getViewController[T <: ViewController]()(implicit ct: ClassTag[T]): Option[T] = {

    val viewController = applicationEnvironment.getController[T]
    if (viewController.isDefined)
      viewController
    else {
      val bean = getBean[T]()
      bean match {
        case result: T => Some(result)
        case _ => None
      }
    }
  }

  override def toString: String = {
    "%s %s (fxml: %s, gainVisibility: %s)".format(this.getClass.getSimpleName, hashCode(), isLoadedFromFXML, gainVisibility)
  }
}
