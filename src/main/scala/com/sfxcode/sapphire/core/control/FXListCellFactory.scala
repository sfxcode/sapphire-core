package com.sfxcode.sapphire.core.control

import com.sfxcode.sapphire.core.value.FXBean
import javafx.scene.control._
import javafx.util.Callback

import scala.beans.BeanProperty

class FXListCellFactory[S <: AnyRef] extends Callback[ListView[FXBean[S]], ListCell[FXBean[S]]] {
  @BeanProperty
  var property = ""

  def call(column: ListView[FXBean[S]]): ListCell[FXBean[S]] =
    new FXListCell[FXBean[S]](property)

}

object FXListCellFactory {

  def apply[S <: AnyRef](property: String): FXListCellFactory[S] = {
    val result = new FXListCellFactory[S]()
    result.setProperty(property)
    result
  }
}

class FXListCell[R](property: String = "") extends ListCell[R] {

  override def updateItem(item: R, empty: Boolean) {
    super.updateItem(item, empty)
    item match {
      case b: FXBean[_] =>
        val value = b.getValue(property)
        value match {
          case v: Any => textProperty().set(v.toString)
          case _ => textProperty().set("NULL VALUE")
        }
      case b: Any => textProperty().set(b.toString)
      case _ => textProperty().set("")
    }
  }
}
