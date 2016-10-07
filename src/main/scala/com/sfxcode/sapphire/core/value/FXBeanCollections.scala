package com.sfxcode.sapphire.core.value

import scalafx.collections._

object FXBeanCollections {

  def observableMap[K, V <: AnyRef]: ObservableMap[K, FXBean[V]] = {
    new ObservableHashMap[K, FXBean[V]]()
  }

  def observableBufferMap[K, V <: AnyRef]: ObservableMap[K, ObservableBuffer[FXBean[V]]] = {
    new ObservableHashMap[K, ObservableBuffer[FXBean[V]]]()
  }

  def observableHashSetMap[K, V <: AnyRef]: ObservableMap[K, ObservableHashSet[FXBean[V]]] = {
    new ObservableHashMap[K, ObservableHashSet[FXBean[V]]]()
  }

  def observableBuffer[T <: AnyRef]: ObservableBuffer[FXBean[T]] = {
    new ObservableBuffer[FXBean[T]]()
  }

  def observableHashSet[T <: AnyRef]: ObservableHashSet[FXBean[T]] = {
    new ObservableHashSet[FXBean[T]]()
  }

}
