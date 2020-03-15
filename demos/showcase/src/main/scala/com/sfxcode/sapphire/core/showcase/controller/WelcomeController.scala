package com.sfxcode.sapphire.core.showcase.controller

import com.sandec.mdfx.MDFXNode
import com.typesafe.scalalogging.LazyLogging
import javafx.fxml.FXML
import javafx.geometry.Insets
import javafx.scene.layout.HBox

import scala.io.Source

class WelcomeController extends BaseController with LazyLogging {

  @FXML var welcomeBox: HBox = _

  override def startup(): Unit = {
    super.startup()
    logMethod("startup")
  }

  override def didInitialize(): Unit = {
    super.didInitialize()
    logMethod("didInitialize")
  }

  // Method for condititional loading, securing access, and so on
  override def canGainVisibility(): Boolean = {
    val result = super.canGainVisibility()
    logMethod("canGainVisibility")
    result
  }

  override def willGainVisibility(): Unit = {
    super.willGainVisibility()
    logMethod("willGainVisibility")
  }

  override def didGainVisibility(): Unit = {
    super.didGainVisibility()
    logMethod("didGainVisibility")
  }

  // called only once
  override def didGainVisibilityFirstTime() {
    super.didGainVisibilityFirstTime()
    val docs = Source.fromURL(getClass.getResource("/markdown/SapphireCoreShowcase.md")).getLines.mkString("\n")
    val css = applicationController.showcaseController.markdownCss
    val markdown = new MDFXNode(docs)
    markdown.getStylesheets.add(css)
    markdown.setPadding(new Insets(4))
    welcomeBox.getChildren.add(markdown)
    logMethod("didGainVisibilityFirstTime")

  }

  override def willLooseVisibility(): Unit = {
    super.willLooseVisibility()
    logMethod("willLooseVisibility")
  }

  override def didLooseVisibility(): Unit = {
    super.didLooseVisibility()
    logMethod("didLooseVisibility")
  }

  def logMethod(name: String): Unit =
    logger.info("class: %s - action: %s (root: %s, parent: %S)".format(this, name, parent, rootPane))

}
