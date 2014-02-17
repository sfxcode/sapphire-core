package com.sfxcode.sapphire.core.cdi


import org.apache.deltaspike.cdise.api.CdiContainerLoader

object CDILauncher {
  private var initialized = false

  def init() {
    if (!initialized) {
      val CdiContainer = CdiContainerLoader.getCdiContainer
      CdiContainer.boot()
      initialized = true
    }
  }

}

