package com.sfxcode.sapphire.core.demo.issues

import com.sfxcode.sapphire.core.application.BaseApplication
import com.sfxcode.sapphire.core.controller.BaseApplicationController
import com.sfxcode.sapphire.core.demo.issues.controller.IssueTrackingLiteController

case class EmptyName(name: String)

// #Application

object Application extends BaseApplication {
  override val applicationController: BaseApplicationController = new ApplicationController
}
// #Application

// #ApplicationController

class ApplicationController extends BaseApplicationController {
  lazy val mainController: IssueTrackingLiteController = getController[IssueTrackingLiteController]()

  def applicationDidLaunch() {
    replaceSceneContent(mainController)
  }
}

// #ApplicationController
