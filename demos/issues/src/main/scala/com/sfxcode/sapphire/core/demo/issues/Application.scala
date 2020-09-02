package com.sfxcode.sapphire.core.demo.issues

import com.sfxcode.sapphire.core.application.AbstractApplication
import com.sfxcode.sapphire.core.controller.AbstractApplicationController
import com.sfxcode.sapphire.core.demo.issues.controller.IssueTrackingLiteController

case class EmptyName(name: String)

// #Application

object Application extends AbstractApplication {
  override val applicationController: AbstractApplicationController = new ApplicationController
}
// #Application

// #ApplicationController

class ApplicationController extends AbstractApplicationController {
  lazy val mainController: IssueTrackingLiteController = getController[IssueTrackingLiteController]()

  def applicationDidLaunch() {
    replaceSceneContent(mainController)
  }
}

// #ApplicationController
