package com.sfxcode.sapphire.core.fxml

import javafx.{util => jfxu}

import com.sfxcode.sapphire.core.cdi.BeanResolver
import com.sfxcode.sapphire.core.controller.{FxmlLoading, ViewController}
import com.sfxcode.sapphire.core.value.ReflectionTools

import scala.reflect.ClassTag
import scalafxml.core.{ControllerDependencyResolver, FxmlProxyGenerator, NoDependencyResolver}

trait ScalaFxmlLoading extends FxmlLoading with BeanResolver{

  def getScalaController[T <: ViewController](fxml: String = "", dependencyResolver: ControllerDependencyResolver = NoDependencyResolver)(implicit ct: ClassTag[T]): T = {
    var fxmlPath = guessFxmlPath(fxml, ct)
    val callback = new jfxu.Callback[Class[_], Object] {
      override def call(cls: Class[_]): Object = FxmlProxyGenerator(cls, dependencyResolver)
    }
    val loadResult = loader.loadFromDocument(fxmlPath.toString, callback)

    val result = getBean[T]()
    result.fxml = loadResult._1
    result.rootPane = loadResult._2

    val impl = ReflectionTools.getMemberValue(result.fxml, "impl")
    if (ReflectionTools.getFieldType(impl, "viewController").isDefined)
      ReflectionTools.setMemberValue(impl, "viewController", result)

    applicationEnvironment.controllerMap.put(result.getClass.getName, result)
    result
  }


}
