package com.sfxcode.sapphire.core.cdi.provider

import javax.enterprise.inject.Produces
import javax.inject.Singleton

import scalafx.application.JFXApp.Parameters

@Singleton
class ApplicationParametersProvider {

  def setParameters(p: Parameters) {
    parameters = p
  }

  @Produces def getParameters: Parameters = {
    parameters
  }

  private var parameters: Parameters = null
}
