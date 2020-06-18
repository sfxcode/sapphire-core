package com.sfxcode.sapphire.core.control

import javafx.scene.control.cell.TextFieldTableCell
import javafx.scene.control.{ TableCell, TableColumn }
import javafx.util.Callback

import scala.util.Try

class FXTableCellFactory[S, T] extends Callback[TableColumn[S, T], TableCell[S, T]] with CellFactory[S, T] {
  private val TextFieldTableCellClassName = "TextFieldTableCell"

  def call(column: TableColumn[S, T]): TableCell[S, T] = {
    val result = createTableCellInstance
    updateCell(result)
    result
  }

  def createTableCellInstance: TableCell[S, T] = {
    var result: TableCell[S, T] = new TextFieldTableCell[S, T]()
    Try {
      if (!TextFieldTableCellClassName.equals(simpleClassName)) {
        val className = packageName + simpleClassName
        result = Class.forName(className).getDeclaredConstructor().newInstance().asInstanceOf[TableCell[S, T]]
      }
    }
    result
  }

  def defaultClassName: String = TextFieldTableCellClassName

}
