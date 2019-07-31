package com.sfxcode.sapphire.core.scene

import javafx.beans.property.Property
import javafx.scene.Node

import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks._

class NodePropertyResolver {
  val resolverBuffer = new ArrayBuffer[NodePropertyResolving]()
  addResolver(DefaultResolver())

  def addResolver(resolver: NodePropertyResolving): Unit = {
    resolverBuffer.+=(resolver)
  }

  def resolve(node: Node): Option[Property[_]] = {
    var maybeProperty: Option[Property[_]] = None
    breakable {
      resolverBuffer.foreach(r => {
        val result = r.resolve(node)
        if (result.isDefined) {
          maybeProperty = result
          break()
        }
      })
    }
    maybeProperty
  }

}

object NodePropertyResolver {
  def apply(): NodePropertyResolver = {
    new NodePropertyResolver
  }
}
