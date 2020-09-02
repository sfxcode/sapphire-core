package com.sfxcode.sapphire.core.control

import javafx.scene.control.cell.TextFieldTreeTableCell
import javafx.scene.control.{ TreeTableCell, TreeTableColumn }
import javafx.util.Callback

import scala.util.Try

class FXTreeTableCellFactory[S, T]
  extends Callback[TreeTableColumn[S, T], TreeTableCell[S, T]]
  with FXCellFactory[S, T] {
  private val TextFieldTreeTableCellClassName = "TextFieldTreeTableCell"

  def call(column: TreeTableColumn[S, T]): TreeTableCell[S, T] = {
    val result = createTableCellInstance
    updateCell(result)
    result
  }

  def createTableCellInstance: TreeTableCell[S, T] = {
    var result: TreeTableCell[S, T] = new TextFieldTreeTableCell[S, T]()
    Try {
      if (!TextFieldTreeTableCellClassName.equals(simpleClassName)) {
        val className = packageName + simpleClassName
        result = Class.forName(className).getDeclaredConstructor().newInstance().asInstanceOf[TreeTableCell[S, T]]
      }
    }
    result
  }

  def defaultClassName: String = TextFieldTreeTableCellClassName

}
