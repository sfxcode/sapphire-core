package com.sfxcode.sapphire.core.demo.windows

import com.sfxcode.sapphire.core.controller.DefaultWindowController
import com.sfxcode.sapphire.core.demo.windows.controller.MainViewController
import javax.enterprise.context.ApplicationScoped
import javax.inject.Named

@Named
@ApplicationScoped
class ApplicationController extends DefaultWindowController {

  lazy val mainViewController: MainViewController =
    getController[MainViewController]()

  def applicationDidLaunch() {
    logger.info("start " + this)
    replaceSceneContent(mainViewController)
  }

}
