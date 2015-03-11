package com.sfxcode.sapphire.core.test

import com.typesafe.scalalogging.LazyLogging
import org.scalameter._
import org.specs2.mutable.Specification
import org.specs2.specification._

trait BeforeAfterAll extends Specification with LazyLogging {

  override def map(fs: =>core.Fragments) = super.map(fs).prepend(fragmentFactory.step(beforeAllInternal())).append(fragmentFactory.step(afterAllInternal()))

  private def beforeAllInternal(): Unit = {
    val time = measure {
      beforeAll()
    }
    logger.trace(s"beforeAll time: $time ms")
  }

  private def afterAllInternal(): Unit = {
    val time = measure {
      afterAll()
    }
    logger.trace(s"afterAll time: $time ms")
  }

  protected def beforeAll()

  protected def afterAll()

}