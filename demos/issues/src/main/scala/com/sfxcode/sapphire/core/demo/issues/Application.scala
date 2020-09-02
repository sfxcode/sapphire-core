package com.sfxcode.sapphire.core.demo.issues

import com.sfxcode.sapphire.core.application.FXApp
import com.sfxcode.sapphire.core.controller.DefaultWindowController
import com.sfxcode.sapphire.core.demo.issues.controller.IssueTrackingLiteController

case class EmptyName(name: String)

// #Application

object Application extends FXApp {
  override val applicationController: DefaultWindowController = new ApplicationController
}
// #Application

// #ApplicationController

class ApplicationController extends DefaultWindowController {
  lazy val mainController: IssueTrackingLiteController = getController[IssueTrackingLiteController]()

  def applicationDidLaunch() {
    replaceSceneContent(mainController)
  }

}

// #ApplicationController
