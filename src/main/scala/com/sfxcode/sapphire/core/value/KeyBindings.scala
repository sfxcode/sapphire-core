package com.sfxcode.sapphire.core.value

import scala.collection.mutable
import javafx.util.StringConverter

class KeyBindings {
  private val bindingMap = new mutable.HashMap[String, String]()

  def apply(key: String) = bindingMap(key)

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

  def keys = bindingMap.keys

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

}


