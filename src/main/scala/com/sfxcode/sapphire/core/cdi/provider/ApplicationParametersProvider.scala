package com.sfxcode.sapphire.core.cdi.provider

import javafx.application.Application.Parameters
import javax.enterprise.inject.Produces
import javax.inject.Singleton

@Singleton
class ApplicationParametersProvider {

  private var parameters: Parameters = _

  @Produces def getParameters: Parameters =
    parameters

  def setParameters(p: Parameters) {
    parameters = p
  }
}
