package com.sfxcode.sapphire.core.demo.test.controller

import com.sfxcode.sapphire.core.controller.ViewController
import com.typesafe.scalalogging.LazyLogging

abstract class AbstractWorkspaceController extends ViewController with LazyLogging{

  override def startup() {
    logger.info("%s - %s".format(this, "startup"))
  }


  override def didInitialize()  {
    logger.info("%s - %s".format(this, "didInitialize"))
  }

  override def willGainVisibility() {
    logger.info("%s - %s".format(this, "willGainVisibility"))
  }

  override def didGainVisibilityFirstTime() {
    logger.info("%s - %s".format(this, "didGainVisibilityFirstTime"))
  }

  override def didGainVisibility() {
    logger.info("%s - %s".format(this, "didGainVisibility"))
  }


  override def willLooseVisibility() {
    logger.info("%s - %s".format(this, "willLooseVisibility"))
  }

  override def didLooseVisibility() {
    logger.info("%s - %s".format(this, "didLooseVisibility"))
  }

  override def shutdown() {
    logger.info("%s - %s".format(this, "shutdown"))
  }


}
