
package com.sfxcode.sapphire.core.demo.appdemo.controller

import com.sfxcode.sapphire.core.controller.ViewController
import com.typesafe.scalalogging.LazyLogging

abstract class AbstractWorkspaceController extends ViewController with LazyLogging{

  override def startup() {
    logger.debug("%s - %s".format(this, "startup"))
  }


  override def didInitialize()  {
    logger.debug("%s - %s".format(this, "didInitialize"))
  }

  override def willGainVisibility() {
    logger.debug("%s - %s".format(this, "willGainVisibility"))
  }

  override def didGainVisibilityFirstTime() {
    logger.debug("%s - %s".format(this, "didGainVisibilityFirstTime"))
  }

  override def didGainVisibility() {
    logger.debug("%s - %s".format(this, "didGainVisibility"))
  }


  override def willLooseVisibility() {
    logger.debug("%s - %s".format(this, "willLooseVisibility"))
  }

  override def didLooseVisibility() {
    logger.debug("%s - %s".format(this, "didLooseVisibility"))
  }

  override def shutdown() {
    logger.debug("%s - %s".format(this, "shutdown"))
  }


}
