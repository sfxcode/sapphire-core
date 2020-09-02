package com.sfxcode.sapphire.core.showcase.controller

import com.sfxcode.sapphire.core.controller.ViewController
import com.sfxcode.sapphire.core.showcase.ApplicationController

trait BaseController extends ViewController {

  def applicationController: ApplicationController = {
    val result = registeredBean[ApplicationController]()
    result.get
  }

  def showcaseController: ShowcaseViewController = applicationController.showcaseController

  def updateShowcaseContent(controller: ViewController): Unit =
    showcaseController.updateShowcaseContent(controller)
}
