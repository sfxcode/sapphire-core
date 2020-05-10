package com.sfxcode.sapphire.core.fxml

import scala.reflect.runtime.universe
import scala.reflect.runtime.universe._

case class FxmlLoader(path: String) extends scala.annotation.StaticAnnotation

object FxmlLoader {

  def pathValue(clazzTag: scala.reflect.ClassTag[_]): String = {
    var result           = ""
    val runtimeClass     = clazzTag.runtimeClass
    val rootMirror       = universe.runtimeMirror(runtimeClass.getClassLoader)
    val myAnnotatedClass = rootMirror.classSymbol(runtimeClass)
    val annotation: Option[universe.Annotation] =
      myAnnotatedClass.annotations.find(_.tree.tpe =:= universe.typeOf[FxmlLoader])
    annotation.flatMap { a =>
      a.tree.children.tail.collectFirst {
        case Literal(Constant(name: String)) =>
          result = name
      }
    }
    result
  }

}
