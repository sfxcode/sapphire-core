package com.sfxcode.sapphire.core.application

import com.sfxcode.sapphire.core.cdi.{ AbstractBeanResolver, SimpleBeanResolver }
import com.sfxcode.sapphire.core.controller.DefaultWindowController
import com.sfxcode.sapphire.core.stage.StageSupport
import javafx.application.Application

// #FXApp
trait FXApp extends StageSupport {

  val beanResolver: AbstractBeanResolver = new SimpleBeanResolver

  def main(args: Array[String]): Unit = {
    FXApp.App = this
    Application.launch(classOf[FXApplication], args: _*)
  }

  val applicationController: DefaultWindowController

  def applicationWillLaunch() {}

  def applicationWillTerminate() {}

}

object FXApp {
  var Application: Application = _
  var App: FXApp = _
}

// #FXApp
