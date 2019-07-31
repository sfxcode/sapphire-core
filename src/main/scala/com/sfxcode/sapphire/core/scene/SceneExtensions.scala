package com.sfxcode.sapphire.core.scene

import javafx.scene.Node

object SceneExtensions {

  implicit class ExtendedNode(val node: Node) extends AnyVal {

    def onMouseClicked(action: => Unit): Unit = {
      node.setOnMouseClicked(event => {
        if (event.isPrimaryButtonDown) {
          action
        }
      })
    }

    def onMouseDoubleClicked(action: => Unit): Unit = {
      node.setOnMouseClicked(event => {
        if (event.isPrimaryButtonDown && 2 == event.getClickCount) {
          action
        }
      })
    }

  }

}
