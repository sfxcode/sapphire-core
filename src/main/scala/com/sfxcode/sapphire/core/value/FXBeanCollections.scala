package com.sfxcode.sapphire.core.value

import java.util

import javafx.collections._

object FXBeanCollections {

  def observableMap[K, V <: AnyRef]: ObservableMap[K, FXBean[V]] = {
    FXCollections.observableHashMap[K, FXBean[V]]()
  }

  def observableList[T <: AnyRef]: ObservableList[FXBean[T]] = {
    FXCollections.observableArrayList[FXBean[T]]()
  }

  def observableHashSet[T <: AnyRef]: ObservableSet[FXBean[T]] = {
    FXCollections.observableSet(new util.HashSet[FXBean[T]]())
  }

}
