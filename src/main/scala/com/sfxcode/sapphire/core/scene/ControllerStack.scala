package com.sfxcode.sapphire.core.scene

import com.sfxcode.sapphire.core.controller.ViewController

import scala.collection.mutable

case class ControllerState(controller: ViewController, stateMap: Map[String, Any] = Map())

case class ControllerStack(contentManager: ContentManager) {
  private val stack = new mutable.ArrayStack[ControllerState]()

  def push(viewController: ViewController): Unit = {
    if (viewController != null) {
      stack.push(ControllerState(viewController, viewController.stateMap))
    }
  }

  def pop(): Option[ViewController] = {
    if (stack.nonEmpty) {
      val controllerState = stack.pop()
      Some(updateContent(controllerState))
    }
    None
  }

  private def updateContent[T <: ViewController](controllerState: ControllerState): T = {
    val viewController: ViewController = controllerState.controller
    contentManager.updatePaneContent(viewController, pushToStack = false)
    viewController.updateFromStateMap(controllerState.stateMap)

    viewController.asInstanceOf[T]
  }

  def popUntil[T <: ViewController](): Option[T] = {
    while (stack.nonEmpty) {
      val result = stack.pop()
      result.controller match {
        case controller: T => return Some(updateContent[T](result))
        case _             =>
      }
    }
    None
  }

  def size: Int = stack.size

}
