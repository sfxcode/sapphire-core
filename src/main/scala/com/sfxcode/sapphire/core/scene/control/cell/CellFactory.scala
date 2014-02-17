package com.sfxcode.sapphire.core.scene.control.cell


import javafx.util.{StringConverter, Callback}
import javafx.scene.control.{TableCell, TableColumn}
import javafx.scene.control.cell.{TextFieldTableCell, CheckBoxTableCell}
import javafx.scene.text.TextAlignment
import javafx.geometry.Pos
import beans.BeanProperty
import com.sfxcode.sapphire.core.value.ConverterFactory

abstract class CustomCellFactory[S, T] extends Callback[TableColumn[S, T], TableCell[S, T]] {

  @BeanProperty
  var alignment = TextAlignment.LEFT

  @BeanProperty
  var converter: String = _

  def updateCell(column: TableColumn[S, T], cell: TableCell[S, T]): TableCell[S, T] = {
    if (alignment == TextAlignment.CENTER) {
      cell.setAlignment(Pos.CENTER)
    }
    else if (alignment == TextAlignment.RIGHT) {
      cell.setAlignment(Pos.CENTER_RIGHT)
    }
    else
      cell.setAlignment(Pos.CENTER_LEFT)

    if (converter != null) {
      cell match {
        case textFieldCell: TextFieldTableCell[S, T] => textFieldCell.setConverter(getConverterForName(converter))
      }
    }

    def getConverterForName(name:String):StringConverter[T] = {
      ConverterFactory.getConverterByName(name)
    }

    cell
  }
}


class CheckBoxCellFactory[S, T] extends CustomCellFactory[S, T] {

  def call(column: TableColumn[S, T]): TableCell[S, T] = {
    updateCell(column, new CheckBoxTableCell[S, T]())
  }
}

class TextFieldCellFactory[S, T] extends CustomCellFactory[S, T] {

  def call(column: TableColumn[S, T]): TableCell[S, T] = {
    updateCell(column, new TextFieldTableCell[S, T]())
  }
}
