package com.sfxcode.sapphire.core.scene

import javafx.scene.control._
import javafx.scene.{Node, Scene}
import javafx.stage.Stage

trait NodeLocator {

  def stage: Stage

  def scene: Scene

  def locateInternal(nodeId: String, parent: Node = null): Option[Node] = {
    if (parent == null) {
      val lookupResult = scene.lookup(nodeId)
      if (lookupResult != null)
        Some(lookupResult)
      else {
        val lookupByIdResult = scene.lookup("#" + nodeId)
        Option(lookupByIdResult)
      }
    } else {
      val lookupResult = parent.lookup(nodeId)
      if (lookupResult != null)
        Some(lookupResult)
      else {
        val lookupByIdResult = parent.lookup("#" + nodeId)
        Option(lookupByIdResult)
      }
    }

  }

  def locate[A <: Node](nodeId: String, parent: Node = null): Option[A] = {
    val result = locateInternal(nodeId, parent)
    if (result.isDefined && result.get.isInstanceOf[A])
      result.asInstanceOf[Option[A]]
    else
      None
  }

  def locateTextField(nodeId: String, parent: Node = null): Option[TextField] = {
    locate[TextField](nodeId, parent)
  }

  def locateLabel(nodeId: String, parent: Node = null): Option[Label] = {
    locate[Label](nodeId, parent)
  }

  def locateComboBox[T <: Any](nodeId: String, parent: Node = null): Option[ComboBox[T]] = {
    locate[ComboBox[T]](nodeId, parent)
  }

  def locateButton(nodeId: String, parent: Node = null): Option[Button] = {
    locate[Button](nodeId, parent)
  }

}
