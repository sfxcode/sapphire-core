package com.sfxcode.sapphire.core.control

import javafx.beans.value.ObservableValue
import javafx.scene.control.TreeTableColumn
import javafx.util.Callback

class FXTreeTableValueFactory[S <: AnyRef, T]
    extends Callback[TreeTableColumn.CellDataFeatures[S, T], ObservableValue[T]]
    with ValueFactory[S, T] {

  def call(features: TreeTableColumn.CellDataFeatures[S, T]): ObservableValue[T] = {
    val value: S = features.getValue.getValue
    resolveValue(value)
  }

}
