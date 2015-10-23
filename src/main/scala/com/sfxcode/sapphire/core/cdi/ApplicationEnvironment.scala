package com.sfxcode.sapphire.core.cdi

import javax.enterprise.context.ApplicationScoped
import javax.inject.Named

import com.sfxcode.sapphire.core.controller.{AppController, ViewController}
import com.sfxcode.sapphire.core.scene.NodePropertyResolver

import scala.reflect.ClassTag
import scalafx.collections.ObservableMap
import scalafx.scene.Scene
import scalafx.stage.Stage

@Named
@ApplicationScoped
class ApplicationEnvironment extends Serializable{
  lazy val controllerMap = ObservableMap[String, ViewController]()
  var nodePropertyResolver = NodePropertyResolver()

  var stage: Stage = _

  var scene: Scene = _

  var applicationController: AppController = _

  var actualSceneController: ViewController = _

  def getController[T <: ViewController](implicit ct: ClassTag[T]): Option[T] = {
    val key = ct.runtimeClass.getName
    if (controllerMap.isDefinedAt(key))
      Some(controllerMap.get(key).asInstanceOf[T])
    else
      None
  }

}
