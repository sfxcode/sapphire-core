package com.sfxcode.sapphire.core.value

import scalafx.collections.ObservableBuffer
import javafx.collections.ObservableList

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

  implicit def collectionToObservableList[T <:AnyRef](collection:Iterable[T]):ObservableList[FXBean[T]] = {
    ObservableBuffer(collection.map(item => FXBean[T](item)).toList)
  }

}
