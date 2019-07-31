package com.sfxcode.sapphire.core.demo.tutorial

import com.sfxcode.sapphire.core.application.FXApp

object Application extends FXApp {
  override def height: Int = 555

  override def width: Int = 700

  override def forceMaxWidth: Boolean = true

  override def forceMaxHeight: Boolean = true
}

