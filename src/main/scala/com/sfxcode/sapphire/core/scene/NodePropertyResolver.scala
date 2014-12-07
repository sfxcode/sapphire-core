package com.sfxcode.sapphire.core.scene

import javafx.scene.Node

import scala.collection.mutable.ArrayBuffer
import scalafx.beans.property.Property

class NodePropertyResolver {
  val resolverBuffer = new ArrayBuffer[NodePropertyResolving]()
  addResolver(DefaultResolver())

  def addResolver(resolver: NodePropertyResolving) = {
    resolverBuffer.+=(resolver)
  }

  def resolve(node: Node): Option[Property[_, _ <: Any]] = {
    resolverBuffer.foreach(r => {
      val result = r.resolve(node)
      if (result.isDefined)
        return result
    })
    None
  }

}

object NodePropertyResolver {
  def apply(): NodePropertyResolver = {
    new NodePropertyResolver
  }
}
