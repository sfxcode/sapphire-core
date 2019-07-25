package com.sfxcode.sapphire.core.cdi.provider

import javafx.application.Application.Parameters
import javax.enterprise.inject.Produces
import javax.inject.Singleton


@Singleton
class ApplicationParametersProvider {

  def setParameters(p: Parameters) {
    parameters = p
  }

  @Produces def getParameters: Parameters = {
    parameters
  }

  private var parameters: Parameters = _
}
