package com.sfxcode.sapphire.core.el

import javafx.collections.FXCollections
import javax.el.{ValueExpression, VariableMapper}

class BaseVariableMapper extends VariableMapper {
  val map = FXCollections.emptyObservableMap[String, ValueExpression]()

  def resolveVariable(variable: String): ValueExpression = {
    map.getOrDefault(variable, null)
  }

  def removeVariable(variable: String) {
    map.remove(variable)
  }

  def setVariable(variable: String, expression: ValueExpression): ValueExpression = {
    map.put(variable, expression)
    expression
  }
}

object BaseVariableMapper {
  def apply(): BaseVariableMapper = new BaseVariableMapper
}