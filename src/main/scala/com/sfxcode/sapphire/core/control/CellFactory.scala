package com.sfxcode.sapphire.core.control

import com.sfxcode.sapphire.core.application.ConverterProvider
import javafx.geometry.Pos
import javafx.scene.control.IndexedCell
import javafx.scene.control.cell.{ TextFieldTableCell, TextFieldTreeTableCell }
import javafx.scene.text.TextAlignment
import javafx.util.StringConverter

import scala.beans.BeanProperty

trait CellFactory[S, T] {

  @BeanProperty
  var packageName: String = "javafx.scene.control.cell."

  @BeanProperty
  var simpleClassName: String = defaultClassName

  @BeanProperty
  var alignment: Any = "left"

  @BeanProperty
  var converter: String = _

  def defaultClassName: String

  protected def updateCell(cell: IndexedCell[T]): Unit = {
    if (alignment == TextAlignment.CENTER || alignment.toString.equalsIgnoreCase("center"))
      cell.setAlignment(Pos.CENTER)
    else if (alignment == TextAlignment.RIGHT || alignment.toString.equalsIgnoreCase("right"))
      cell.setAlignment(Pos.CENTER_RIGHT)
    else
      cell.setAlignment(Pos.CENTER_LEFT)

    if (converter != null)
      cell match {
        case textFieldCell: TextFieldTableCell[S, T] => textFieldCell.setConverter(getConverterForName(converter))
        case textFieldCell: TextFieldTreeTableCell[S, T] => textFieldCell.setConverter(getConverterForName(converter))
      }

    def getConverterForName(name: String): StringConverter[T] =
      ConverterProvider.getConverterByName(name)

  }

}
