package com.sfxcode.sapphire.core.fxml

import scala.reflect.runtime.universe
import scala.reflect.runtime.universe._

case class FxmlLocation(path: String) extends scala.annotation.StaticAnnotation

object FxmlLocation {

  def pathValue(clazzTag: scala.reflect.ClassTag[_]): String = {
    var result = ""
    val runtimeClass = clazzTag.runtimeClass
    val rootMirror = universe.runtimeMirror(runtimeClass.getClassLoader)
    val myAnnotatedClass = rootMirror.classSymbol(runtimeClass)
    val annotation: Option[universe.Annotation] =
      myAnnotatedClass.annotations.find(_.tree.tpe =:= universe.typeOf[FxmlLocation])
    annotation.flatMap { a =>
      a.tree.children.tail.collectFirst {
        case Literal(Constant(name: String)) =>
          result = name
      }
    }
    result
  }

}
