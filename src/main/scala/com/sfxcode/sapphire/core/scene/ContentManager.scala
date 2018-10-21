package com.sfxcode.sapphire.core.scene

import javax.enterprise.event.Event
import javax.inject.{ Inject, Named }

import com.sfxcode.sapphire.core.controller.ViewController
import com.typesafe.scalalogging.LazyLogging
import org.apache.deltaspike.core.api.provider.BeanProvider

import scalafx.beans.property.{ BooleanProperty, IntegerProperty }
import scalafx.scene.Node
import scalafx.scene.layout.Pane

case class ContentWillChangeEvent(pane: Pane, parentController: ViewController, newController: ViewController, oldController: ViewController)

case class ContentDidChangeEvent(pane: Pane, parentController: ViewController, newController: ViewController, oldController: ViewController)

@Named
class ContentManager extends LazyLogging {
  private val controllerStack = ControllerStack(this)
  val stackSize = IntegerProperty(0)
  var useStack = BooleanProperty(false)

  var contentPane: Pane = _

  var parentController: ViewController = _
  var actualController: ViewController = _
  var lastController: ViewController = _

  @Inject var contentWillChange: Event[ContentWillChangeEvent] = _
  @Inject var contentChanged: Event[ContentDidChangeEvent] = _

  private def removePaneContent(node: Node) {
    contentPane.getChildren.remove(node)
  }

  private def addPaneContent(node: Node) {
    contentPane.getChildren.add(node)
  }

  def addToStack(viewController: ViewController): Unit = {
    controllerStack.push(viewController)
    stackSize.value = controllerStack.size
  }

  def loadFromStack(): Option[ViewController] = {
    val result = controllerStack.pop()
    stackSize.value = controllerStack.size
    result
  }

  def loadFromStackUntil[T <: ViewController](): Option[T] = {
    val result = controllerStack.popUntil[T]()
    stackSize.value = controllerStack.size
    result
  }

  def enableStack(): Unit = useStack.set(true)

  def disableStack(): Unit = useStack.set(false)

  def switchToLast(): Unit = {
    updatePaneContent(lastController)
  }

  def updatePaneContent(newController: ViewController, pushToStack: Boolean = true) {
    val oldController = actualController
    if (newController != null && newController != oldController && newController.canGainVisibility
      && (oldController == null || oldController.shouldLooseVisibility)) {
      if (oldController != null)
        try {
          oldController.willLooseVisibility()
        } catch {
          case e: Exception => logger.error(e.getMessage, e)
        }

      try {
        newController.willGainVisibility()
      } catch {
        case e: Exception => logger.error(e.getMessage, e)
      }
      contentWillChange.fire(ContentWillChangeEvent(contentPane, parentController, newController, actualController))

      if (oldController != null) {
        removePaneContent(oldController.rootPane)
        oldController.managedParent.value = null
        parentController.removeChildViewController(oldController)
        try {
          oldController.didLooseVisibility()
        } catch {
          case e: Exception => logger.error(e.getMessage, e)
        }
      }

      lastController = oldController
      if (useStack.value && pushToStack)
        controllerStack.push(oldController)

      addPaneContent(newController.rootPane)
      newController.managedParent.value = parentController

      if (!newController.gainVisibility) {
        try {
          newController.didGainVisibilityFirstTime()
        } catch {
          case e: Exception => logger.error(e.getMessage, e)
        }
        newController.gainVisibility = true
      }

      try {
        newController.didGainVisibility()
        parentController.addChildViewController(newController)
      } catch {
        case e: Exception => logger.error(e.getMessage, e)
      }

      contentChanged.fire(ContentDidChangeEvent(contentPane, parentController, newController, actualController))
      actualController = newController
    }
  }

}

object ContentManager {

  def apply(contentPane: Pane, parentController: ViewController = null, startController: ViewController = null): ContentManager = {

    val result = BeanProvider.getContextualReference("contentManager", false, classOf[ContentManager])
    result.contentPane = contentPane
    result.parentController = parentController
    result.updatePaneContent(startController)
    result
  }
}
