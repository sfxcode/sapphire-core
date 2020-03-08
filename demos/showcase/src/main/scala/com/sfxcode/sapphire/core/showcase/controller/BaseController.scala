package com.sfxcode.sapphire.core.showcase.controller

import com.sfxcode.sapphire.core.controller.ViewController
import com.sfxcode.sapphire.core.showcase.ApplicationController

trait BaseController extends ViewController {

  // Load applicationController by CDI
  def applicationController: ApplicationController = {
    val result = getBean[ApplicationController]()
    result
  }

  def showcaseController: ShowcaseViewController = applicationController.showcaseController

  def updateShowcaseContent(controller: ViewController): Unit =
    showcaseController.updateShowcaseContent(controller)
}
