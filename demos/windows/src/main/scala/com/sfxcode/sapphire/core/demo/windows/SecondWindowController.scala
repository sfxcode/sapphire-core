package com.sfxcode.sapphire.core.demo.windows

import com.sfxcode.sapphire.core.controller.AdditionalWindowController
import com.sfxcode.sapphire.core.demo.windows.controller.AdditionalViewController
import javax.enterprise.context.ApplicationScoped
import javax.inject.Named

@Named
@ApplicationScoped
class SecondWindowController extends AdditionalWindowController {

  lazy val viewController: AdditionalViewController =
    getController[AdditionalViewController]()

  override def startup(): Unit = {
    super.startup()
    initStage()
    replaceSceneContent(viewController)
  }

  override def width: Int = 200
}
