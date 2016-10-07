package com.sfxcode.sapphire.core.demo.appdemo.controller

import com.sfxcode.sapphire.core.controller.ViewController

import scalafx.scene.layout.HBox

class WorkspaceController extends ViewController {

  var name: String = ""

  rootPane = new HBox()

  override def didGainVisibilityFirstTime() {
    super.willGainVisibility()
  }

  override def didGainVisibility() {
    super.didGainVisibility()
  }

}

