package com.sfxcode.sapphire.core.cdi

import org.apache.deltaspike.cdise.api.CdiContainerLoader

object CDILauncher {
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

}
