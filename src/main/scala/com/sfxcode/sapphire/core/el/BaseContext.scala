package com.sfxcode.sapphire.core.el

import javax.el._

import de.odysseus.el.ObjectValueExpression
import de.odysseus.el.misc.TypeConverter


class BaseContext extends ELContext {
  val resolver =   BaseResolver()
  val functionMapper = BaseFunctionMapper()
  val variableMapper =  BaseVariableMapper()

  def getELResolver: ELResolver = resolver

  def getFunctionMapper: FunctionMapper = functionMapper

  def getVariableMapper: VariableMapper = variableMapper

  def register(name: String, obj: Any) {
    variableMapper.setVariable(name, createValueExpression(obj))
  }

  def unregister(name: String) {
    variableMapper.removeVariable(name)
  }

  def createValueExpression(obj: Any): ObjectValueExpression = {
    new ObjectValueExpression(TypeConverter.DEFAULT, obj, obj.getClass)
  }

}

object BaseContext {
  def apply():BaseContext = new BaseContext
}



