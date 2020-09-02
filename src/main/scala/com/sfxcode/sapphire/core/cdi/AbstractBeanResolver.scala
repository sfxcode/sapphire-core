package com.sfxcode.sapphire.core.cdi

import scala.reflect.ClassTag

abstract class AbstractBeanResolver {

  def getResolverName: String = getClass.getSimpleName

  start()

  def start() {}

  def getBean[T <: AnyRef](optional: Boolean = false)(implicit ct: ClassTag[T]): T

  def stop() {}

}
