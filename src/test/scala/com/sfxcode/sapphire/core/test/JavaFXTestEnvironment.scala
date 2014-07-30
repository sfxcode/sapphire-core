package com.sfxcode.sapphire.core.test

import javafx.embed.swing.JFXPanel

import com.sfxcode.sapphire.core.cdi.CDILauncher
import com.typesafe.scalalogging.LazyLogging

/**
 * Created by tom on 30.07.14.
 */
object JavaFXTestEnvironment extends LazyLogging {
  private var initialized = false

  def init() {
    if (!initialized)  {
      // init CDI if needed
      CDILauncher.init()
      // start JFXPanel for toolkit initialization
      new JFXPanel
      logger.info("toolkit initialized")
      initialized = true
    }

  }

}
