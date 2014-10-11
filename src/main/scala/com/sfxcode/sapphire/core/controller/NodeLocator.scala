package com.sfxcode.sapphire.core.controller

import javafx.scene.Node

import scalafx.stage.Stage
import scalafx.scene.Scene
import scalafx.scene.control._

trait NodeLocator {

  def stage: Stage = ApplicationEnvironment.stage

  def scene: Scene = ApplicationEnvironment.scene

  def locateInternal(nodeId: String, parent: Node = null): Option[Node] = {
    if (parent == null) {
      val delegate = scene.delegate
      var result = delegate.lookup(nodeId)
      if (result != null)
        return Some(result)
      result = delegate.lookup("#" + nodeId)
      result
      if (result != null)
        return Some(result)
    }
    else {
      var result = parent.lookup(nodeId)
      if (result != null)
        return Some(result)
      result = parent.lookup("#" + nodeId)
      result
      if (result != null)
        return Some(result)
    }

    None
  }

  def locateTextField(nodeId: String): Option[TextField] = {
    val result = locateInternal(nodeId)
    if (result.isDefined && result.get.isInstanceOf[javafx.scene.control.TextField])
      return Some(new TextField(result.get.asInstanceOf[javafx.scene.control.TextField]))
    None
  }

  def locateLabel(nodeId: String): Option[Label] = {
    val result = locateInternal(nodeId)
    if (result.isDefined && result.get.isInstanceOf[javafx.scene.control.Label])
      return Some(new Label(result.get.asInstanceOf[javafx.scene.control.Label]))
    None
  }

}
