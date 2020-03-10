package com.sfxcode.sapphire.core.control

import com.sfxcode.sapphire.core.cdi.BeanResolver
import com.sfxcode.sapphire.core.cdi.provider.ConverterProvider
import javafx.geometry.Pos
import javafx.scene.control.cell.TextFieldTableCell
import javafx.scene.control.{ TableCell, TableColumn }
import javafx.scene.text.TextAlignment
import javafx.util.{ Callback, StringConverter }

import scala.beans.BeanProperty
import scala.util.Try

class FXTableCellFactory[S, T] extends Callback[TableColumn[S, T], TableCell[S, T]] with BeanResolver {

  private val TextFieldTableCellClassName = "TextFieldTableCell"

  @BeanProperty
  var packageName: String = "javafx.scene.control.cell."

  @BeanProperty
  var simpleClassName: String = TextFieldTableCellClassName

  @BeanProperty
  var alignment: Any = "left"

  @BeanProperty
  var converter: String = _

  def updateCell(column: TableColumn[S, T], cell: TableCell[S, T]): TableCell[S, T] = {
    if (alignment == TextAlignment.CENTER || alignment.toString.equalsIgnoreCase("center")) {
      cell.setAlignment(Pos.CENTER)
    } else if (alignment == TextAlignment.RIGHT || alignment.toString.equalsIgnoreCase("right")) {
      cell.setAlignment(Pos.CENTER_RIGHT)
    } else
      cell.setAlignment(Pos.CENTER_LEFT)

    if (converter != null) {
      cell match {
        case textFieldCell: TextFieldTableCell[S, T] => textFieldCell.setConverter(getConverterForName(converter))
      }
    }

    def getConverterForName(name: String): StringConverter[T] = {
      val converterProvider = getBean[ConverterProvider]()
      converterProvider.getConverterByName(name)
    }

    cell
  }

  def call(column: TableColumn[S, T]): TableCell[S, T] =
    updateCell(column, createTableCellInstance)

  private def createTableCellInstance: TableCell[S, T] = {
    var result: TableCell[S, T] = new TextFieldTableCell[S, T]()
    Try {
      if (!TextFieldTableCellClassName.equals(simpleClassName)) {
        val className = packageName + simpleClassName
        result = Class.forName(className).getDeclaredConstructor().newInstance().asInstanceOf[TableCell[S, T]]
      }
    }
    result
  }
}
