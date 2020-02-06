package com.sfxcode.sapphire.core.demo.windows

import com.sfxcode.sapphire.core.cdi.BeanResolver
import com.sfxcode.sapphire.core.controller.DefaultWindowController
import com.sfxcode.sapphire.core.demo.windows.controller.MainViewController
import javax.enterprise.context.ApplicationScoped
import javax.inject.Named

@Named
@ApplicationScoped
class ApplicationController extends DefaultWindowController with BeanResolver {

  lazy val mainViewController: MainViewController =
    getController[MainViewController]()

  lazy val secondWindowController = getBean[SecondWindowController]()
  lazy val thirdWindowController  = getBean[ThirdWindowController]()

  def applicationDidLaunch() {
    logger.info("start " + this)
    replaceSceneContent(mainViewController)
  }

}
