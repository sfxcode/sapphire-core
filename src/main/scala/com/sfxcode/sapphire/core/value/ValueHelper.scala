package com.sfxcode.sapphire.core.value

import javafx.beans.value._
import javafx.beans._

object ValueHelper {

  def addChangeListener[T <: Any](property: ObservableValue[T], func: (T, T) => Unit): ChangeListener[T] = {
    val listener: PropertyChangeListener2[T] = new PropertyChangeListener2[T](func)
    property.addListener(listener)
    listener
  }

  def addChangeListener[T <: Any](property: ObservableValue[T], func: (T) => Unit): ChangeListener[T] = {
    val listener: PropertyChangeListener1[T] = new PropertyChangeListener1[T](func)
    property.addListener(listener)
    listener
  }

  def addInvalidationListener[T <: Any](property: ObservableValue[T], func: (Observable) => Unit): InvalidationListener = {
    val listener: PropertyInvalidationListener = new PropertyInvalidationListener(func)
    property.addListener(listener)
    listener
  }


  private class PropertyChangeListener3[T](func: (ObservableValue[_ <: T], T, T) => Unit) extends ChangeListener[T] {

    def changed(ov: ObservableValue[_ <: T], oldValue: T, newValue: T) {
      func(ov, oldValue, newValue)
    }
  }

  private class PropertyChangeListener2[T](func: (T, T) => Unit) extends ChangeListener[T] {

    def changed(ov: ObservableValue[_ <: T], oldValue: T, newValue: T) {
      func(oldValue, newValue)
    }
  }

  private class PropertyChangeListener1[T](func: (T) => Unit) extends ChangeListener[T] {

    def changed(ov: ObservableValue[_ <: T], oldValue: T, newValue: T) {
      func(newValue)
    }
  }

  private class PropertyInvalidationListener(func: (Observable) => Unit) extends InvalidationListener {
    def invalidated(observable: Observable) {
      func(observable)
    }
  }


}
