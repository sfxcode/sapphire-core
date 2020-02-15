package com.sfxcode.sapphire.core.demo.tutorial

import com.sfxcode.sapphire.core.scene.{ ContentDidChangeEvent, ContentWillChangeEvent }
import com.typesafe.scalalogging.LazyLogging
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.event.Observes
import javax.inject.Named

// #cdi
@Named
@ApplicationScoped
class ApplicationObserver extends LazyLogging {

  def listenToWillChange(@Observes event: ContentWillChangeEvent) {
    logger.debug(event.toString)
  }

  def listenToDidChange(@Observes event: ContentDidChangeEvent) {
    logger.debug(event.toString)
  }
}
// #cdi
