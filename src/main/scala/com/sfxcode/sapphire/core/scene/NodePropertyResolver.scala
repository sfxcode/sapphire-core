package com.sfxcode.sapphire.core.scene

import javafx.beans.property.Property
import javafx.scene.Node

import scala.collection.mutable.ArrayBuffer

class NodePropertyResolver {
  val resolverBuffer = new ArrayBuffer[NodePropertyResolving]()
  addResolver(DefaultResolver())

  def addResolver(resolver: NodePropertyResolving): Unit = {
    resolverBuffer.+=(resolver)
  }

  def resolve(node: Node): Option[Property[_]] = {
    resolverBuffer.foreach(r => {
      val result = r.resolve(node)
      if (result.isDefined) {
        return result
      }
    })
    None
  }

}

object NodePropertyResolver {
  def apply(): NodePropertyResolver = {
    new NodePropertyResolver
  }
}
