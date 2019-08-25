package com.sfxcode.sapphire.core.collections

import com.sfxcode.sapphire.core.collections.CollectionExtensions.ChangeState.ChangeState
import javafx.beans.{InvalidationListener, Observable}
import javafx.collections._

import scala.collection.JavaConverters._

object CollectionExtensions {

  object ChangeState extends Enumeration {
    type ChangeState = Value
    val ADD, REMOVE, REPLACE, UNKNOWN = Value
  }

  implicit class ExtendedObservableList[A](val list: ObservableList[A]) extends AnyVal {

    def replaceByFilteredValues(f: A => Boolean): ObservableList[A] = {
      FXCollections.observableArrayList(list.asScala.filter(f).asJava)
    }

    def map[B](f: A => B): TraversableOnce[B] = {
      list.asScala.map(f)
    }

    def foreach[U](f: A => U): Unit = {
      list.asScala.foreach(f)
    }

    def addChangeListener(f: ListChangeListener.Change[_ <: A] => Unit): Unit = {
      list.addListener(new ListChangeListener[A] {
        override def onChanged(change: ListChangeListener.Change[_ <: A]): Unit = {
          f(change)
        }
      })
    }

    def addInvalidationListener(f: Observable => Unit): Unit = {
      list.addListener(new InvalidationListener {
        override def invalidated(observable: Observable): Unit = {
          f(observable)
        }
      })
    }

  }

  implicit class ExtendedObservableMap[K, V](val map: ObservableMap[K, V]) extends AnyVal {

    def addMapChangeListener(f: (ChangeState, K, V, V) => Unit): Unit = {

      map.addListener(new MapChangeListener[K, V] {
        def onChanged(change: MapChangeListener.Change[_ <: K, _ <: V]): Unit = {
          if (change.wasAdded() && change.wasRemoved()) {
            f(ChangeState.REPLACE, change.getKey, change.getValueAdded, change.getValueRemoved)
          } else if (change.wasAdded()) {
            f(ChangeState.ADD, change.getKey, change.getValueAdded, change.getValueRemoved)
          } else if (change.wasRemoved()) {
            f(ChangeState.REPLACE, change.getKey, change.getValueAdded, change.getValueRemoved)
          } else {
            f(ChangeState.UNKNOWN, change.getKey, change.getValueAdded, change.getValueRemoved)
          }
        }
      })
    }
  }

}
