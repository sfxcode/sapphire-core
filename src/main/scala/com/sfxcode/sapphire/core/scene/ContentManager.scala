package com.sfxcode.sapphire.core.scene

import javafx.scene.layout.Pane
import com.sfxcode.sapphire.core.controller.ViewController
import javafx.scene.Node
import javax.inject.{Named, Inject}
import javax.enterprise.event.Event
import org.apache.deltaspike.core.api.provider.BeanProvider
import com.typesafe.scalalogging.slf4j.Logging

case class ContentWillChangeEvent(pane:Pane, parentController:ViewController,newController:ViewController, oldController:ViewController)
case class ContentDidChangeEvent(pane:Pane, parentController:ViewController, newController:ViewController, oldController:ViewController)

@Named
class ContentManager extends Logging{
  var contentPane: Pane = _

  var parentController: ViewController = _
  var actualController: ViewController = _
  var lastController: ViewController = _

  @Inject var contentWillChange:Event[ContentWillChangeEvent] = _
  @Inject var contentChanged:Event[ContentDidChangeEvent] = _

  private def removePaneContent(node: Node) {
    contentPane.getChildren.remove(node)
  }

  private def addPaneContent(node: Node) {
    contentPane.getChildren.add(node)
  }

  def switchToLast() {
    if (lastController != null)
      updatePaneContent(lastController)
  }

  def updatePaneContent(newController: ViewController) {
    val oldController = actualController
    if (newController != null && newController != oldController) {
      if (oldController != null)
        try {
          oldController.willLooseVisibility()
        }
        catch {
          case e:Exception => logger.error(e.getMessage, e)
        }

      try {
        newController.willGainVisibility()
      }
      catch {
        case e:Exception => logger.error(e.getMessage, e)
      }
      contentWillChange.fire(ContentWillChangeEvent(contentPane, parentController,  newController, actualController))

      if (oldController != null) {
        removePaneContent(oldController.rootPane)
        oldController.parent = null
        try {
          oldController.didLooseVisibility()
        }
        catch {
          case e:Exception => logger.error(e.getMessage, e)
        }
      }

      lastController = oldController
      addPaneContent(newController.rootPane)
      newController.parent = parentController

      if (!newController.firstTimeLoaded) {
        try {
          newController.didGainVisibilityFirstTime()
        }
        catch {
          case e:Exception => logger.error(e.getMessage, e)
        }
        newController.firstTimeLoaded = true
      }

      try {
        newController.didGainVisibility()
      }
      catch {
        case e:Exception => logger.error(e.getMessage, e)
      }

      contentChanged.fire(ContentDidChangeEvent(contentPane, parentController, newController, actualController))
      actualController = newController
    }
  }

}

object ContentManager  {

  def apply(contentPane: Pane, parentController:ViewController=null, startController: ViewController=null): ContentManager = {

    val result = BeanProvider.getContextualReference("contentManager", false, classOf[ContentManager])
    result.contentPane = contentPane
    result.parentController = parentController
    result.updatePaneContent(startController)
    result
  }
}
