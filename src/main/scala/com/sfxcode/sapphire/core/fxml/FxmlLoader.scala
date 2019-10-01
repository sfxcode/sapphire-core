package com.sfxcode.sapphire.core.fxml
import scala.reflect.runtime.ru._
import scala.reflect.runtime.{universe => ru}

case class FxmlLoader(path: String) extends scala.annotation.StaticAnnotation

object FxmlLoader {

  def pathValue(clazzTag: scala.reflect.ClassTag[_]): String = {
    var result = ""
    val runtimeClass = clazzTag.runtimeClass
    val rootMirror = ru.runtimeMirror(runtimeClass.getClassLoader)
    val myAnnotatedClass = rootMirror.classSymbol(runtimeClass)
    val annotation: Option[ru.Annotation] = myAnnotatedClass.annotations.find(_.tree.tpe =:= ru.typeOf[FxmlLoader])
    annotation.flatMap { a =>
      a.tree.children.tail.collectFirst {
        case Literal(Constant(name: String)) =>
          result = name
      }
    }
    result
  }

}
