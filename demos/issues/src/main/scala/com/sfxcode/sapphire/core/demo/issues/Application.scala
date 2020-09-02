package com.sfxcode.sapphire.core.demo.issues

import com.sfxcode.sapphire.core.application.FXApp
import com.sfxcode.sapphire.core.controller.DefaultWindowController
import com.sfxcode.sapphire.core.demo.issues.controller.IssueTrackingLiteController
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces
import javax.inject.Named

case class EmptyName(name: String)

// #Application

//object Application extends FXApp {}
// #Application

// #ApplicationController

@Named
@ApplicationScoped
class ApplicationController extends DefaultWindowController {
  lazy val mainController: IssueTrackingLiteController = getController[IssueTrackingLiteController]()

  def applicationDidLaunch() {
    replaceSceneContent(mainController)
  }

}

// #ApplicationController
