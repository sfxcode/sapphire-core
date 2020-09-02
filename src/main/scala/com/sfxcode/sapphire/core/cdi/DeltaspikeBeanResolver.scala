package com.sfxcode.sapphire.core.cdi

import org.apache.deltaspike.cdise.api.CdiContainerLoader
import org.apache.deltaspike.core.api.provider.BeanProvider

import scala.reflect.ClassTag

class DeltaspikeBeanResolver extends AbstractBeanResolver {
  private var initialized = false

  override def start() {
    if (!initialized) {
      val container = CdiContainerLoader.getCdiContainer
      container.boot()
      initialized = true
    }
  }

  override def stop() {
    if (initialized) {
      val container = CdiContainerLoader.getCdiContainer
      container.shutdown()
      initialized = false
    }
  }

  def getBean[T <: AnyRef](optional: Boolean = false)(implicit ct: ClassTag[T]): T = {
    val clazz = ct.runtimeClass
    BeanProvider.getContextualReference(clazz, optional).asInstanceOf[T]
  }

  def isInitialized: Boolean = initialized

}
