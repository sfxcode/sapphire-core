package com.sfxcode.sapphire.core.controller

import com.sfxcode.sapphire.core.event.ActionEvents
import javax.inject.Inject
import com.typesafe.config.Config
import javafx.fxml.Initializable
import java.net.URL
import java.util.ResourceBundle

abstract class ViewController extends NodeLocator with FxmlLoading with ActionEvents with Initializable {

  implicit def stringListToMap(list:List[String]):Map[String, String] = list.map(s=> (s,s)).toMap

  @Inject
  var configuration: Config = _

  var parent:ViewController = _

  var firstTimeLoaded = false

  override def initialize(loc: URL, res: ResourceBundle): Unit = {
    location = loc
    resources = res
  }

  def willGainVisibility() {}

  def didGainVisibility() {}

  def didGainVisibilityFirstTime() {}

  def willLooseVisibility() {}

  def didLooseVisibility() {}

  def startup() {}

  def shutdown() {}

  def actualSceneController: ViewController = ApplicationEnvironment.actualSceneController

  def isActualSceneController = this == actualSceneController


  override def toString: String = {
    var result = this.getClass.getSimpleName
    if (isLoadedFromFXML)
      result += " fxml: " + fxml
    result
  }
}
