package com.sfxcode.sapphire.core.controller

import javax.inject.Inject
import com.sfxcode.sapphire.core.cdi.BeanResolver
import com.typesafe.config.Config
import javafx.fxml.Initializable
import java.net.URL
import java.util.ResourceBundle
import javax.annotation.{PreDestroy, PostConstruct}

abstract class ViewController extends NodeLocator  with FxmlLoading with BeanResolver with ActionEvents with Initializable {

  implicit def stringListToMap(list:List[String]):Map[String, String] = list.map(s=> (s,s)).toMap

  @Inject
  var configuration: Config = _

  var parent:ViewController = _

  var firstTimeLoaded = false

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

  def didInitialize()  {}

  def willGainVisibility() {}

  def didGainVisibility() {}

  def didGainVisibilityFirstTime() {}

  def willLooseVisibility() {}

  def didLooseVisibility() {}


  def actualSceneController: ViewController = ApplicationEnvironment.actualSceneController

  def isActualSceneController = this == actualSceneController


  override def toString: String = {
    var result = this.getClass.getSimpleName
    if (isLoadedFromFXML)
      result += " fxml: " + fxml
    result
  }
}
