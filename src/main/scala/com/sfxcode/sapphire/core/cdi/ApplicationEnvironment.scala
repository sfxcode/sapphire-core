package com.sfxcode.sapphire.core.cdi

import javax.enterprise.context.ApplicationScoped
import javax.inject.Named

import com.sfxcode.sapphire.core.controller.{AppController, ViewController}
import com.sfxcode.sapphire.core.el.Expressions
import com.sfxcode.sapphire.core.fxml.FxmlExpressionResolver
import com.sfxcode.sapphire.core.scene.NodePropertyResolver

import scala.reflect.ClassTag
import scalafx.collections.ObservableMap
import scalafx.scene.Scene
import scalafx.stage.Stage

@Named
@ApplicationScoped
class ApplicationEnvironment extends Serializable{

  var controllerMap = ObservableMap[String, ViewController]()

  var nodePropertyResolver = NodePropertyResolver()

  var fxmlExpressionResolver = new FxmlExpressionResolver[String,Any]

  var stage: Stage = _

  var scene: Scene = _

  var applicationController: AppController = _

  var actualSceneController: ViewController = _

  def registerController(controller:ViewController):Unit = {
    controllerMap.put(controller.getClass.getName, controller)
    val simpleName:String = controller.getClass.getSimpleName
    val expressionName = "%s%s".format(simpleName.head.toLower,simpleName.tail)
    Expressions.register(expressionName, controller)
  }

  def getController[T <: ViewController](implicit ct: ClassTag[T]): Option[T] = {
    val key = ct.runtimeClass.getName
    if (controllerMap.isDefinedAt(key))
      Some(controllerMap(key).asInstanceOf[T])
    else
      None
  }

}
