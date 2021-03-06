package com.sfxcode.sapphire.core.scene

import com.sfxcode.sapphire.core.controller.ViewController
import com.typesafe.scalalogging.LazyLogging
import javafx.beans.property.{ SimpleBooleanProperty, SimpleIntegerProperty }
import javafx.scene.Node
import javafx.scene.layout.Pane

case class ContentWillChangeEvent(
  pane: Pane,
  parentController: ViewController,
  newController: ViewController,
  oldController: ViewController)

case class ContentDidChangeEvent(
  pane: Pane,
  parentController: ViewController,
  newController: ViewController,
  oldController: ViewController)

class ContentManager extends LazyLogging {
  val stackSize = new SimpleIntegerProperty(0)
  private val controllerStack = ControllerStack(this)
  var useStack = new SimpleBooleanProperty(false)

  var contentPane: Pane = _

  var parentController: ViewController = _
  var actualController: ViewController = _
  var lastController: ViewController = _

  def addToStack(viewController: ViewController): Unit = {
    controllerStack.push(viewController)
    stackSize.setValue(controllerStack.size)
  }

  def loadFromStack(): Option[ViewController] = {
    val result = controllerStack.pop()
    stackSize.setValue(controllerStack.size)
    result
  }

  def loadFromStackUntil[T <: ViewController](): Option[T] = {
    val result = controllerStack.popUntil[T]()
    stackSize.setValue(controllerStack.size)
    result
  }

  def enableStack(): Unit = useStack.set(true)

  def disableStack(): Unit = useStack.set(false)

  def switchToLast(): Unit =
    updatePaneContent(lastController)

  def updatePaneContent(newController: ViewController, pushToStack: Boolean = true) {
    val oldController = actualController
    if (newController != null && newController != oldController && newController.canGainVisibility()
      && (oldController == null || oldController.shouldLooseVisibility)) {
      if (oldController != null)
        try oldController.willLooseVisibility()
        catch {
          case e: Exception => logger.error(e.getMessage, e)
        }

      try {
        newController.windowController.set(parentController.windowController.getValue)
        newController.willGainVisibility()
      } catch {
        case e: Exception => logger.error(e.getMessage, e)
      }

      if (oldController != null) {
        removePaneContent(oldController.rootPane)
        oldController.managedParent.setValue(null)
        parentController.removeChildViewController(oldController)
        try oldController.didLooseVisibility()
        catch {
          case e: Exception => logger.error(e.getMessage, e)
        }
      }

      lastController = oldController
      if (useStack.getValue && pushToStack)
        controllerStack.push(oldController)

      addPaneContent(newController.rootPane)
      newController.managedParent.setValue(parentController)

      if (!newController.gainedVisibility) {
        try newController.didGainVisibilityFirstTime()
        catch {
          case e: Exception => logger.error(e.getMessage, e)
        }
        newController.gainedVisibility = true
      }

      try {
        newController.didGainVisibility()
        parentController.addChildViewController(newController)
      } catch {
        case e: Exception => logger.error(e.getMessage, e)
      }

      actualController = newController
    }
  }

  private def removePaneContent(node: Node) {
    contentPane.getChildren.remove(node)
  }

  private def addPaneContent(node: Node) {
    contentPane.getChildren.add(node)
  }

}

object ContentManager {

  def apply(
    contentPane: Pane,
    parentController: ViewController,
    startController: ViewController = null): ContentManager = {

    if (contentPane == null)
      throw new IllegalArgumentException("contentPane must not be NULL")

    if (parentController == null)
      throw new IllegalArgumentException("parentController must not be NULL")

    val result = new ContentManager
    result.contentPane = contentPane
    result.parentController = parentController
    result.updatePaneContent(startController)
    result
  }
}
