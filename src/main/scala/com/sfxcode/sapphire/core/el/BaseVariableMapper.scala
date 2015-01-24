package com.sfxcode.sapphire.core.el

import javax.el.{ValueExpression, VariableMapper}
import scalafx.collections.ObservableMap

class BaseVariableMapper extends  VariableMapper {
  val map = ObservableMap[String, ValueExpression]()

  def resolveVariable(variable: String): ValueExpression =
  {
    map.getOrElse(variable, null)
  }

  def removeVariable(variable: String) {
    map.remove(variable)
  }


  def setVariable(variable: String, expression: ValueExpression): ValueExpression =
  {
    map.put(variable, expression)
    expression
  }
}

object BaseVariableMapper {
  def apply():BaseVariableMapper = new BaseVariableMapper
}