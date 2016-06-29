package com.sfxcode.sapphire.core.fxml

import javafx.{util => jfxu}

import com.sfxcode.sapphire.core.cdi.BeanResolver
import com.sfxcode.sapphire.core.controller.ViewController
import com.sfxcode.sapphire.core.value.ReflectionTools

import scala.reflect.ClassTag
import scalafxml.core.{ControllerDependencyResolver, FxmlProxyGenerator, NoDependencyResolver}

trait ScalaFxmlLoading extends FxmlLoading with BeanResolver{

  def getScalaController[T <: ViewController](fxml: String = "", dependencyResolver: ControllerDependencyResolver = NoDependencyResolver)(implicit ct: ClassTag[T]): T = {
    val fxmlPath = guessFxmlPath(fxml, ct)
    val callback = new jfxu.Callback[Class[_], Object] {
      override def call(cls: Class[_]): Object = FxmlProxyGenerator(cls, dependencyResolver)
    }

    loader.fxmlLoader.getNamespace.put("expression", applicationEnvironment.fxmlExpressionResolver)

    val loadResult = loader.loadFromDocument(fxmlPath.toString, callback)

    val controller = getBean[T]()
    controller.fxml = loadResult._1
    controller.rootPane = loadResult._2

    val res = applicationEnvironment.applicationController.resourceBundleForView(fxmlPath)
    controller.initialize(controller.getClass.getResource(fxmlPath), res)

    val impl = ReflectionTools.getMemberValue(controller.fxml, "impl")
    if (ReflectionTools.getFieldType(impl, "viewController").isDefined)
      ReflectionTools.setMemberValue(impl, "viewController", controller)

    applicationEnvironment.registerController(controller)
    controller
  }


}
