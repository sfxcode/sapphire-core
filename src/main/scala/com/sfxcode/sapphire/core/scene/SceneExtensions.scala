package com.sfxcode.sapphire.core.scene

import javafx.scene.Node

object SceneExtensions {
  val PrimaryButtonName = "PRIMARY"

  implicit class ExtendedNode(val node: Node) extends AnyVal {

    def onPrimaryButtonClicked(action: => Unit): Unit = {
      node.setOnMouseClicked(event => {
        if (PrimaryButtonName.equals(event.getButton.name())) {
          action
        }
      })
    }

    def onPrimaryButtonDoubleClicked(action: => Unit): Unit = {
      node.setOnMouseClicked(event => {
        if (PrimaryButtonName.equals(event.getButton.name()) && 2 == event.getClickCount) {
          action
        }
      })
    }

  }

}
