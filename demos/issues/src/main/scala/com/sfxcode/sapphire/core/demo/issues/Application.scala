package com.sfxcode.sapphire.core.demo.issues

import com.sfxcode.sapphire.core.application.FXApp
import com.sfxcode.sapphire.core.controller.DefaultWindowController
import com.sfxcode.sapphire.core.demo.issues.controller.IssueTrackingLiteController
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces
import javax.inject.Named

object Application extends FXApp {}

case class EmptyName(name: String)

@Named
@ApplicationScoped
class ApplicationController extends DefaultWindowController {
  lazy val mainController = getController[IssueTrackingLiteController]()

  def applicationDidLaunch() {
    println("start " + this)
    replaceSceneContent(mainController)
  }

  @Produces
  def emptyName: EmptyName = {
    EmptyName("New Issue")
  }

}
