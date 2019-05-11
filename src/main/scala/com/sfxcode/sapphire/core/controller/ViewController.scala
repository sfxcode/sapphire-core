package com.sfxcode.sapphire.core.controller

import java.net.URL
import java.util.ResourceBundle

import com.sfxcode.sapphire.core.base.ConfigValues
import com.sfxcode.sapphire.core.cdi.BeanResolver
import com.sfxcode.sapphire.core.fxml.FxmlLoading
import com.typesafe.scalalogging.LazyLogging
import javafx.fxml.Initializable
import javax.annotation.{ PostConstruct, PreDestroy }
import scalafx.beans.property.ObjectProperty
import scalafx.collections.ObservableBuffer
import scalafx.scene.layout.Pane

import scala.language.implicitConversions
import scala.reflect.ClassTag

abstract class ViewController extends FxmlLoading with BeanResolver with ActionEvents with Initializable with LazyLogging with ConfigValues {

  implicit def stringListToMap(list: List[String]): Map[String, String] = list.map(s => (s, s)).toMap

  val managedParent = new ObjectProperty[ViewController]()

  protected val managedChildren: ObservableBuffer[ViewController] = ObservableBuffer[ViewController]()

  protected val unmanagedChildren: ObservableBuffer[ViewController] = ObservableBuffer[ViewController]()

  def parent: ViewController = managedParent.value

  def addChildViewController(viewController: ViewController): Unit = {
    if (!managedChildren.contains(viewController))
      managedChildren.add(viewController)
  }

  def removeChildViewController(viewController: ViewController): Unit = {
    if (!managedChildren.contains(viewController))
      managedChildren.remove(viewController)
  }

  var gainVisibility = false

  // bean lifecycle

  @PostConstruct
  def postConstruct(): Unit = startup()

  def startup() {}

  @PreDestroy
  def preDestroy(): Unit = shutdown()

  def shutdown() {}

  override def initialize(loc: URL, res: ResourceBundle): Unit = {
    location = loc
    resources = res
    didInitialize()
  }

  // controller lifecycle

  def didInitialize() {}

  def canGainVisibility: Boolean = true

  def willGainVisibility(): Unit = {
    managedChildren.foreach(_.willGainVisibility())
    unmanagedChildren.foreach(_.willGainVisibility())
  }

  def didGainVisibilityFirstTime(): Unit = {

  }

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

  def updatePaneContent(pane: Pane, viewController: ViewController): Boolean = {
    if (pane == null) {
      logger.warn("contentPane is NULL")
      false
    } else {
      if (viewController == null) {
        logger.warn("viewController is NULL")
        false
      } else {
        if (viewController.canGainVisibility)
          try {
            viewController.managedParent.value = this
            viewController.willGainVisibility()
            pane.getChildren.add(viewController.rootPane)
            viewController.didGainVisibility()
            viewController.didGainVisibilityFirstTime()
            unmanagedChildren.add(viewController)
            true
          } catch {
            case e: Exception =>
              logger.error(e.getMessage, e)
              false
          }
        else
          false
      }
    }
  }

  def stateMap: Map[String, Any] = Map[String, Any]()

  def updateFromStateMap(map: Map[String, Any]): Unit = {}

  def actualSceneController: ViewController = applicationEnvironment.actualSceneController

  def isActualSceneController: Boolean = this == actualSceneController

  def getViewController[T <: ViewController]()(implicit ct: ClassTag[T]): Option[T] = {

    val viewController = applicationEnvironment.getController[T]
    if (viewController.isDefined)
      viewController
    else {
      val bean = getBean[T]()
      bean match {
        case result: T => Some(result)
        case _ => None
      }
    }
  }

  override def toString: String = {
    "%s %s (fxml: %s, gainVisibility: %s)".format(this.getClass.getSimpleName, hashCode(), isLoadedFromFXML, gainVisibility)
  }
}
