package com.sfxcode.sapphire.core.cdi

import javafx.collections.{ FXCollections, ObservableMap }

import scala.reflect.ClassTag

class SimpleBeanResolver extends AbstractBeanResolver {
  val beanMap: ObservableMap[Class[_], Any] = FXCollections.observableHashMap[Class[_], Any]()

  override def stop(): Unit = {
    super.stop()
    beanMap.clear()
  }

  def getBean[T <: AnyRef](optional: Boolean = false)(implicit ct: ClassTag[T]): T = {
    val clazz = ct.runtimeClass
    if (!beanMap.containsKey(clazz))
      beanMap.put(clazz, clazz.getDeclaredConstructor().newInstance())
    beanMap.get(clazz).asInstanceOf[T]
  }
}
