package com.sfxcode.sapphire.core.demo.windows

import com.sfxcode.sapphire.core.cdi.BeanResolver
import com.sfxcode.sapphire.core.controller.{AdditionalWindowController, DefaultWindowController}
import com.sfxcode.sapphire.core.demo.windows.controller.{AdditionalViewController, MainViewController}
import javax.enterprise.context.ApplicationScoped
import javax.inject.Named

@Named
@ApplicationScoped
class ApplicationController extends DefaultWindowController with BeanResolver {

  lazy val mainViewController: MainViewController =
    getController[MainViewController]()

  // #SecondWindowControllerVar
  lazy val secondWindowController = getBean[SecondWindowController]()
  // #SecondWindowControllerVar
  lazy val thirdWindowController = getBean[ThirdWindowController]()

  def applicationDidLaunch() {
    logger.info("start " + this)
    replaceSceneContent(mainViewController)
  }

}

// #AdditionalWindowController
abstract class AbstractWindowController extends AdditionalWindowController {

  lazy val viewController: AdditionalViewController =
    getController[AdditionalViewController]()

  override def startup(): Unit = {
    super.startup()
    initStage()
    replaceSceneContent(viewController)
  }

  override def width: Int = 200
}
// #AdditionalWindowController

// #SecondWindowController
@Named
@ApplicationScoped
class SecondWindowController extends AbstractWindowController
// #SecondWindowController

@Named
@ApplicationScoped
class ThirdWindowController extends AbstractWindowController
