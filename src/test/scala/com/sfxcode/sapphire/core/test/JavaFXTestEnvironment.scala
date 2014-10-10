package com.sfxcode.sapphire.core.test

import javafx.embed.swing.JFXPanel

import com.sfxcode.sapphire.core.cdi.CDILauncher
import com.sfxcode.sapphire.core.demo.test.Application
import com.typesafe.scalalogging.LazyLogging
import org.apache.deltaspike.core.api.provider.BeanManagerProvider

import scalafx.application.JFXApp.PrimaryStage


object  JavaFXTestEnvironment extends LazyLogging {
  private var initialized = false

  def init() {
    if (!initialized)  {
      new JFXPanel
      CDILauncher.init()

      // init CDI if needed
      // start JFXPanel for toolkit initialization
      logger.info("toolkit initialized")
      initialized = true
    }

  }

}
