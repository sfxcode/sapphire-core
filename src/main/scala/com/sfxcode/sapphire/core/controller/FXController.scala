package com.sfxcode.sapphire.core.controller

import javax.inject.Inject

import scala.reflect.ClassTag
import com.sfxcode.sapphire.core.cdi.FXMLHandler
import org.apache.deltaspike.core.api.provider.BeanProvider
import com.typesafe.config.ConfigFactory

trait FXController {

  @Inject
  var loader: FXMLHandler = _

  def getController[T <: ViewController](fxml: String = "")(implicit ct: ClassTag[T]): T = {

    if (fxml.isEmpty) {
      var basePath = ConfigFactory.load().getString("sapphire.core.fxml.basePath")
      if (basePath.isEmpty) {
        val guessed = ct.runtimeClass.getName.replace(".", "/").replace("Controller", "")
        loader.getViewController[T]("/%s.fxml".format(guessed))
      }
      else {
        val fxmlName = ct.runtimeClass.getSimpleName.replace("Controller", "")
        loader.getViewController[T]("%S%s.fxml".format(basePath, fxmlName))
      }
    }
    else
      loader.getViewController[T](fxml.toString)
  }


  def getBean[T <: AnyRef](optional: Boolean = false)(implicit ct: ClassTag[T]): T = {
    val clazz = ct.runtimeClass
    BeanProvider.getContextualReference(clazz, optional).asInstanceOf[T]
  }

}
