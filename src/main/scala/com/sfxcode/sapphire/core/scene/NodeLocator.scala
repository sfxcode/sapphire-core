package com.sfxcode.sapphire.core.scene

import javafx.scene.Node
import scalafx.delegate.SFXDelegate
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.stage.Stage

import scala.reflect.ClassTag

trait NodeLocator {

  def stage: Stage

  def scene: Scene

  def locateInternal(nodeId: String, parent: Node = null): Option[Node] = {
    if (parent == null) {
      val delegate = scene.delegate
      val lookupResult = delegate.lookup(nodeId)
      if (lookupResult != null)
        Some(lookupResult)
      else {
        val lookupByIdResult = delegate.lookup("#" + nodeId)
        Option(lookupByIdResult)
      }
    } else {
      val lookupResult = parent.lookup(nodeId)
      if (lookupResult != null)
        Some(lookupResult)
      else {
        val lookupByIdResult = parent.lookup("#" + nodeId)
        Option(lookupByIdResult)
      }
    }

  }

  def locate[A <: Node](nodeId: String, parent: Node = null): Option[A] = {
    val result = locateInternal(nodeId, parent)
    if (result.isDefined && result.get.isInstanceOf[A])
      result.asInstanceOf[Option[A]]
    else
      None
  }

  def locateSFX[A <: Node, B <: SFXDelegate[A]](nodeId: String, parent: Node = null)(implicit ct: ClassTag[B]): Option[B] = {
    val option = locate[A](nodeId, parent)
    if (option.isDefined) {
      val constructors = ct.runtimeClass.getConstructors.filter(c => {
        c.getParameters.length == 1 && "delegate".equals(c.getParameters.head.getName)
      })

      if (constructors.length == 1) {
        val instance = constructors.head.newInstance(option.get)
        Some(instance.asInstanceOf[B])
      } else
        None
    } else
      None
  }

  def locateTextField(nodeId: String, parent: Node = null): Option[TextField] = {
    locateSFX[javafx.scene.control.TextField, TextField](nodeId, parent)
  }

  def locateLabel(nodeId: String, parent: Node = null): Option[Label] = {
    locateSFX[javafx.scene.control.Label, Label](nodeId, parent)
  }

  def locateComboBox[T <: Any](nodeId: String, parent: Node = null): Option[ComboBox[T]] = {
    locateSFX[javafx.scene.control.ComboBox[T], ComboBox[T]](nodeId, parent)
  }

  def locateButton(nodeId: String, parent: Node = null): Option[Button] = {
    locateSFX[javafx.scene.control.Button, Button](nodeId, parent)
  }

}
