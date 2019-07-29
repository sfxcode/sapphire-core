package com.sfxcode.sapphire.core.value

import javafx.collections.{FXCollections, ObservableList}

import scala.collection.JavaConverters._

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

  implicit def collectionToObservableList[T <: AnyRef](collection: Iterable[T]): ObservableList[FXBean[T]] = {
    FXCollections.observableList[FXBean[T]](collection.map(item => FXBean[T](item)).toList.asJava)
  }

  implicit def observableListToCollection[T <: AnyRef](buffer: ObservableList[FXBean[T]]): Iterable[T] = {
    buffer.asScala.map(item => item.bean)
  }

}
