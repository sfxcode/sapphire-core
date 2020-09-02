package com.sfxcode.sapphire.core.application

import scala.reflect.ClassTag

trait BeanResolver {

  def getBean[T <: AnyRef](optional: Boolean = false)(implicit ct: ClassTag[T]): T =
    FXApp.App.beanResolver.getBean[T](optional)

}
