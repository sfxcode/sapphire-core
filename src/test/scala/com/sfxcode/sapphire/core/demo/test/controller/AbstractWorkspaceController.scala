package com.sfxcode.sapphire.core.demo.test.controller

import com.sfxcode.sapphire.core.controller.ViewController

abstract class AbstractWorkspaceController extends ViewController{

  override def startup() {
    println("%s - %s".format(this, "startup"))
  }


  override def didInitialize()  {
    println("%s - %s".format(this, "didInitialize"))
  }

  override def willGainVisibility() {
    println("%s - %s".format(this, "willGainVisibility"))
  }

  override def didGainVisibilityFirstTime() {
    println("%s - %s".format(this, "didGainVisibilityFirstTime"))
  }

  override def didGainVisibility() {
    println("%s - %s".format(this, "didGainVisibility"))
  }


  override def willLooseVisibility() {
    println("%s - %s".format(this, "willLooseVisibility"))
  }

  override def didLooseVisibility() {
    println("%s - %s".format(this, "didLooseVisibility"))
  }

  override def shutdown() {
    println("%s - %s".format(this, "shutdown"))
  }


}
