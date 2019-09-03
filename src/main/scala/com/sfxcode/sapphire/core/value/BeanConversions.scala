package com.sfxcode.sapphire.core.value

import com.sfxcode.sapphire.core.CollectionExtensions._
import javafx.collections.ObservableList

import scala.collection.JavaConverters._
import scala.language.implicitConversions

trait BeanConversions {

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

  implicit def beansToObservableList[T <: AnyRef](iterable: Iterable[T]): ObservableList[FXBean[T]] = {
    iterable.map(item => FXBean[T](item))
  }

  implicit def observableListToBeans[T <: AnyRef](list: ObservableList[FXBean[T]]): Iterable[T] = {
    list.asScala.map(item => item.bean)
  }

}
