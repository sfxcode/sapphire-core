package com.sfxcode.sapphire.core.controller

import javax.inject.Inject

import scala.reflect.ClassTag
import com.sfxcode.sapphire.core.cdi.FXMLHandler
import org.apache.deltaspike.core.api.provider.BeanProvider

trait FXController {

  @Inject
  var loader: FXMLHandler = _

  def getController[T <: ViewController](fxml: String="")(implicit ct: ClassTag[T]): T = {

    if (fxml.isEmpty) {
      val guessed = ct.runtimeClass.getName.replace(".", "/").replace("Controller", "")
      loader.getViewController[T]("/%s.fxml".format(guessed))
    }
    else
      loader.getViewController[T](fxml.toString)
  }

 
  def getBean[T <: AnyRef](optional: Boolean = false)(implicit ct: ClassTag[T]): T = {
    val clazz = ct.runtimeClass
    BeanProvider.getContextualReference(clazz, optional).asInstanceOf[T]
  }

}
