package com.sfxcode.sapphire.core.controller

import scalafx.stage.Stage
import scalafx.scene.Scene
import scala.collection.mutable
import scala.reflect.ClassTag

object ApplicationEnvironment   {
  lazy val  controllerMap = new mutable.HashMap[String, ViewController]()

  var stage: Stage = _

  var scene: Scene = _

  var applicationController:AppController = _

  var actualSceneController:ViewController = _

  def getController[T <: ViewController](implicit ct: ClassTag[T]): T = {
    val clazz = ct.runtimeClass
    controllerMap(clazz.getName).asInstanceOf[T]
  }

}
