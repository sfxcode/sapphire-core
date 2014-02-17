package com.sfxcode.sapphire.core.controller

import javafx.fxml.Initializable
import java.net.URL
import java.util.ResourceBundle
import scalafx.scene.layout.Pane
import com.sfxcode.sapphire.core.event.ActionEvents
import javax.inject.Inject
import com.typesafe.config.Config

abstract class ViewController extends NodeLocator with FXController with ActionEvents with Initializable {
  implicit def stringListToMap(list:List[String]):Map[String, String] = list.map(s=> (s,s)).toMap

  @Inject
  var configuration: Config = _

  var location: URL = _
  var resources: ResourceBundle = _

  var rootPane: Pane = _

  var parent:ViewController = _

  var firstTimeLoaded = false

  def initialize(location: URL, resources: ResourceBundle) {
    this.location = location
    this.resources = resources
    startup()
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

  def isLoadedFromFXML = location != null

  override def toString: String = {
    var result = this.getClass.getSimpleName
    if (isLoadedFromFXML)
      result += " fxml: " + location.toString.substring(location.toString.lastIndexOf("/") + 1)
    result
  }
}
