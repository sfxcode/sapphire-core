package com.sfxcode.sapphire.core.test

import com.sfxcode.sapphire.core.controller.ViewController
import javafx.scene.layout.HBox

class TestViewController extends ViewController {

  var name: String = ""

  rootPane = new HBox()

  override def didGainVisibilityFirstTime() {
    super.willGainVisibility()
  }

  override def didGainVisibility() {
    super.didGainVisibility()
  }

}

