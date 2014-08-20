package com.sfxcode.sapphire.core.controller

import scala.reflect.ClassTag
import scalafx.collections.ObservableMap
import scalafx.scene.Scene
import scalafx.stage.Stage

object ApplicationEnvironment   {
  lazy val  controllerMap = ObservableMap[String, ViewController]()

  var stage: Stage = _

  var scene: Scene = _

  var applicationController:AppController = _

  var actualSceneController:ViewController = _

  def getController[T <: ViewController](implicit ct: ClassTag[T]): T = {
    val clazz = ct.runtimeClass
    controllerMap(clazz.getName).asInstanceOf[T]
  }

}
