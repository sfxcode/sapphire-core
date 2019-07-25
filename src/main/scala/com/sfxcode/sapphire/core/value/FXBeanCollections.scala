package com.sfxcode.sapphire.core.value

import javafx.collections._

object FXBeanCollections {

  def observableMap[K, V <: AnyRef]: ObservableMap[K, FXBean[V]] = {
    FXCollections.emptyObservableMap[K, FXBean[V]]()
  }

  def observableBuffer[T <: AnyRef]: ObservableList[FXBean[T]] = {
    FXCollections.emptyObservableList[FXBean[T]]()
  }

  def observableHashSet[T <: AnyRef]: ObservableSet[FXBean[T]] = {
    FXCollections.emptyObservableSet[FXBean[T]]()
  }

}
