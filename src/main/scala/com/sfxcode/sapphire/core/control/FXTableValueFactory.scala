package com.sfxcode.sapphire.core.control

import javafx.beans.value.ObservableValue
import javafx.scene.control.TableColumn
import javafx.scene.control.TableColumn.CellDataFeatures
import javafx.util.Callback

class FXTableValueFactory[S <: AnyRef, T]
  extends Callback[TableColumn.CellDataFeatures[S, T], ObservableValue[T]]
  with TableValue {

  def call(features: CellDataFeatures[S, T]): ObservableValue[T] = {
    val value: S = features.getValue
    resolveValue[S, T](value)
  }

}
