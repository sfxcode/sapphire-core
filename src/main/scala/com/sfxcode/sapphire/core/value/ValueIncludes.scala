package com.sfxcode.sapphire.core.value

import scalafx.collections.ObservableBuffer

trait ValueIncludes {

  implicit def beanToFXBean[T <: AnyRef](bean: T): FXBean[T] = FXBean(bean)

  implicit def fxBeanToBean[T <: AnyRef](fxBean: FXBean[T]): T = fxBean.bean

  implicit def beanToFXBeanOption[T <: AnyRef](bean: T): Option[FXBean[T]] = {
    if (bean != null)
      Some(FXBean(bean))
    else
      None
  }

  implicit def optionBeanToFXBeanOption[T <: AnyRef](bean: Option[T]): Option[FXBean[T]] = {
    if (bean.isDefined)
      Some(FXBean(bean.get))
    else
      None
  }

  implicit def collectionToObservableBuffer[T <: AnyRef](collection: Iterable[T]): ObservableBuffer[FXBean[T]] = {
    ObservableBuffer(collection.map(item => FXBean[T](item)).toList)
  }

  implicit def observableBufferToCollection[T <: AnyRef](buffer: ObservableBuffer[FXBean[T]]): Iterable[T] = {
    buffer.map(item => item.bean).toIterable
  }

}
