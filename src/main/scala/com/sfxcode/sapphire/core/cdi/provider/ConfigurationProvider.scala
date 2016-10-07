package com.sfxcode.sapphire.core.cdi.provider

import javax.enterprise.inject.Produces
import javax.inject.Singleton

import com.typesafe.config.{Config, ConfigFactory}

@Singleton
class ConfigurationProvider {
  private val configuration = ConfigFactory.load()

  @Produces def getConfiguration: Config = {
    configuration
  }

}
