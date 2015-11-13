package com.sfxcode.sapphire.core.demo.appdemo.controller

import scalafx.scene.layout.HBox

import com.sfxcode.sapphire.core.controller.ViewController

class WorkspaceController extends ViewController  {

  var name:String=""

  rootPane = new HBox()

  override def didGainVisibilityFirstTime() {
    super.willGainVisibility()
  }

  override def didGainVisibility() {
    super.didGainVisibility()
  }

}

