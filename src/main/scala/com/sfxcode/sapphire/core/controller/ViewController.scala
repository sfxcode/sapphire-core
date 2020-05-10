package com.sfxcode.sapphire.core.controller

import java.net.URL
import java.util.ResourceBundle

import com.sfxcode.sapphire.core.CollectionExtensions._
import com.sfxcode.sapphire.core.ConfigValues
import com.sfxcode.sapphire.core.cdi.BeanResolver
import com.sfxcode.sapphire.core.el._
import com.sfxcode.sapphire.core.fxml.FxmlLoading
import com.typesafe.scalalogging.LazyLogging
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.{FXCollections, ObservableList}
import javafx.fxml.Initializable
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.stage.Stage
import javax.annotation.{PostConstruct, PreDestroy}

import scala.language.implicitConversions

abstract class ViewController
    extends FxmlLoading
    with BeanResolver
    with ActionEvents
    with Initializable
    with Expressions
    with LazyLogging
    with ConfigValues {

  implicit def stringListToMap(list: List[String]): Map[String, String] = list.map(s => (s, s)).toMap

  implicit def simpleObjectPropertyToOption[T <: AnyRef](prop: SimpleObjectProperty[T]): Option[T] =
    Option[T](prop.get())

  val windowController = new SimpleObjectProperty[WindowController]()

  val managedParent                                               = new SimpleObjectProperty[ViewController]()
  protected val managedChildren: ObservableList[ViewController]   = FXCollections.observableArrayList[ViewController]()
  protected val unmanagedChildren: ObservableList[ViewController] = FXCollections.observableArrayList[ViewController]()
  var gainedVisibility                                            = false

  def stage: Stage = windowController.get.stage

  def scene: Scene = windowController.get.scene

  def parent: ViewController = managedParent.getValue

  def addChildViewController(viewController: ViewController): Unit =
    if (!managedChildren.contains(viewController))
      managedChildren.add(viewController)

  def removeChildViewController(viewController: ViewController): Unit =
    if (!managedChildren.contains(viewController))
      managedChildren.remove(viewController)

  // bean lifecycle

  @PostConstruct
  def postConstruct(): Unit = {
    registerBean(this)
    startup()
  }

  def startup() {}

  @PreDestroy
  def preDestroy(): Unit = {
    unregisterBean(this)
    shutdown()
  }

  def shutdown() {}

  override def initialize(loc: URL, res: ResourceBundle): Unit = {
    location = Some(loc)
    resources = Some(res)
    didInitialize()
  }

  // controller lifecycle

  def didInitialize() {}

  def canGainVisibility(): Boolean = true

  def willGainVisibility(): Unit = {
    managedChildren.foreach(_.willGainVisibility())
    unmanagedChildren.foreach(_.willGainVisibility())
  }

  def didGainVisibilityFirstTime(): Unit = {}

  def didGainVisibility(): Unit = {
    managedChildren.foreach(_.didGainVisibility())
    unmanagedChildren.foreach(_.didGainVisibility())
  }

  def shouldLooseVisibility: Boolean = true

  def willLooseVisibility(): Unit = {
    managedChildren.foreach(_.willLooseVisibility())
    unmanagedChildren.foreach(_.willLooseVisibility())
  }

  def didLooseVisibility(): Unit = {
    managedChildren.foreach(_.didLooseVisibility())
    unmanagedChildren.foreach(_.didLooseVisibility())
  }

  def updatePaneContent(pane: Pane, viewController: ViewController): Boolean =
    if (pane == null) {
      logger.warn("contentPane is NULL")
      false
    }
    else {
      if (viewController == null) {
        logger.warn("viewController is NULL")
        false
      }
      else {
        if (viewController.canGainVisibility)
          try {
            viewController.managedParent.setValue(this)
            viewController.windowController.set(windowController.get)
            viewController.willGainVisibility()
            pane.getChildren.add(viewController.rootPane)
            viewController.didGainVisibilityFirstTime()
            viewController.didGainVisibility()
            unmanagedChildren.add(viewController)
            true
          }
          catch {
            case e: Exception =>
              logger.error(e.getMessage, e)
              false
          }
        else
          false
      }
    }

  def stateMap: Map[String, Any] = Map[String, Any]()

  def updateFromStateMap(map: Map[String, Any]): Unit = {}

  def isActualSceneController: Boolean = this == actualSceneController

  def actualSceneController: ViewController = windowController.get.actualSceneController

  override def toString: String =
    "%s %s (fxml: %s, gainedVisibility: %s)".format(
      this.getClass.getSimpleName,
      hashCode(),
      isLoadedFromFXML,
      gainedVisibility
    )
}
