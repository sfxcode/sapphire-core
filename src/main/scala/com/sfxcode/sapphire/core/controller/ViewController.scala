package com.sfxcode.sapphire.core.controller

import java.net.URL
import java.util.ResourceBundle
import javafx.fxml.Initializable
import javax.annotation.{PostConstruct, PreDestroy}
import javax.inject.Inject

import com.sfxcode.sapphire.core.cdi.BeanResolver
import com.typesafe.config.Config

abstract class ViewController extends NodeLocator with FxmlLoading with BeanResolver with ActionEvents with Initializable {

  implicit def stringListToMap(list: List[String]): Map[String, String] = list.map(s => (s, s)).toMap

  @Inject
  var configuration: Config = _

  var parent: ViewController = _

  var gainVisibility = false

  // bean lifecycle

  @PostConstruct
  final def postConstruct() = startup()

  def startup() {}

  @PreDestroy
  final def preDestroy() = shutdown()

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


  def actualSceneController: ViewController = ApplicationEnvironment.actualSceneController

  def isActualSceneController = this == actualSceneController


  override def toString: String = {
    "%s (fxml: %s, gainVisibility: %s)".format(this.getClass.getSimpleName, isLoadedFromFXML, gainVisibility)
  }
}
