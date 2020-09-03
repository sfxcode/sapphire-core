package com.sfxcode.sapphire.core.demo.issues.deltaspike

import org.apache.deltaspike.cdise.api.CdiContainerLoader

object DeltaspikeLauncher {
  private var initialized = false

  def init() {
    if (!initialized) {
      val container = CdiContainerLoader.getCdiContainer
      container.boot()
      initialized = true
    }
  }

  def shutdown() {
    if (initialized) {
      val container = CdiContainerLoader.getCdiContainer
      container.shutdown()
      initialized = false
    }
  }

  def isInitialized: Boolean = initialized

}
