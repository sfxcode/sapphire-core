package com.sfxcode.sapphire.core.value

import javafx.collections.{FXCollections, ObservableMap}

import scala.collection.JavaConverters._
import scala.collection.mutable
import scala.reflect.runtime.universe._

class KeyBindings {
  val bindingMap: ObservableMap[String, String] = FXCollections.observableHashMap[String, String]()

  def apply(key: String): String = bindingMap.get(key)

  def add(nodeKey: String, memberKey: String): KeyBindings = {
    bindingMap.put(nodeKey, memberKey)
    this
  }

  def add(list: List[String], nodePrefix: String = ""): KeyBindings = {
    list.foreach(key => {
      bindingMap.put(nodePrefix + key, key)
    })
    this
  }

  def add(map: Map[String, String]): KeyBindings = {
    map.keys.foreach(key => {
      bindingMap.put(key, map(key))
    })
    this
  }

  def keys: mutable.Set[String] = bindingMap.keySet().asScala

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

  def forClass[T <: AnyRef](nodePrefix: String = "")(implicit t: TypeTag[T]): KeyBindings = {
    val bindings = new KeyBindings

    val symbols = ReflectionTools.getMembers[T]()
    symbols.foreach(s => {
      val name = s.name.toString
      bindings.add(nodePrefix + name, name)
    })
    bindings
  }

}

