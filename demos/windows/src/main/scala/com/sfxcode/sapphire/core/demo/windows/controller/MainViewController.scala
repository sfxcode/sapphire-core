package com.sfxcode.sapphire.core.demo.windows.controller

import com.sfxcode.sapphire.core.controller.ViewController

class MainViewController extends ViewController {

  override def startup() {
    println("Test")
  }

  override def didGainVisibilityFirstTime() {
    super.didGainVisibility()
  }
}
