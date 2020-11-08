package com.sfxcode.sapphire.core.value

import com.sfxcode.sapphire.data.reflect.{ FieldRegistry, ReflectionTools }
import javafx.collections.{ FXCollections, ObservableMap }

import scala.jdk.CollectionConverters._
import scala.collection.mutable
import scala.reflect.ClassTag
import scala.reflect.runtime.universe._

class KeyBindings {
  val bindingMap: ObservableMap[String, String] = FXCollections.observableHashMap[String, String]()

  def apply(key: String): String = bindingMap.get(key)

  def add(nodeKey: String, memberKey: String): KeyBindings = {
    bindingMap.put(nodeKey, memberKey)
    this
  }

  def add(list: List[String], nodePrefix: String = ""): KeyBindings = {
    list.foreach(key => bindingMap.put(nodePrefix + key, key))
    this
  }

  def add(map: Map[String, String]): KeyBindings = {
    map.keys.foreach(key => bindingMap.put(key, map(key)))
    this
  }

  def keys: mutable.Set[String] = bindingMap.keySet().asScala

  override def toString: String = super.toString + " Bindings: " + bindingMap
}

object KeyBindings {

  def apply(): KeyBindings = new KeyBindings

  def apply(args: String*): KeyBindings = {
    val bindings = new KeyBindings
    bindings.add(args.toList)
  }

  def apply(map: Map[String, String]): KeyBindings = {
    val bindings = new KeyBindings
    bindings.add(map)
  }

  def apply(list: List[String], nodePrefix: String = ""): KeyBindings = {
    val bindings = new KeyBindings
    bindings.add(list, nodePrefix)
  }

  def forClass[T <: AnyRef](nodePrefix: String = "")(implicit ct: ClassTag[T]): KeyBindings = {
    val bindings = new KeyBindings

    val fields = FieldRegistry.fieldMap(ct.runtimeClass)
    fields.keys.foreach { name =>
      bindings.add(nodePrefix + name, name)
    }
    bindings

  }

}
