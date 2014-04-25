package com.sfxcode.sapphire.core.controller

import com.sfxcode.sapphire.core.event.ActionEvents
import javax.inject.Inject
import com.typesafe.config.Config

abstract class ViewController extends NodeLocator with FXController with ActionEvents {
  implicit def stringListToMap(list:List[String]):Map[String, String] = list.map(s=> (s,s)).toMap

  @Inject
  var configuration: Config = _

  var parent:ViewController = _

  var firstTimeLoaded = false

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
