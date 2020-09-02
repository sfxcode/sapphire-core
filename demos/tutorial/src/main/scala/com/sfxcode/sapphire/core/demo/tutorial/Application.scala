package com.sfxcode.sapphire.core.demo.tutorial

import com.sfxcode.sapphire.core.application.FXApp
import com.sfxcode.sapphire.core.cdi.{ AbstractBeanResolver, DeltaspikeBeanResolver, SimpleBeanResolver }
import com.sfxcode.sapphire.core.controller.DefaultWindowController

object Application extends FXApp {

  val applicationController: DefaultWindowController = beanResolver.getBean[ApplicationController]()

  override def height: Int = 555

  override def width: Int = 700

  override def forceMaxWidth: Boolean = true

  override def forceMaxHeight: Boolean = true
}
