package com.sfxcode.sapphire.core.el

import de.odysseus.el.ObjectValueExpression
import de.odysseus.el.misc.TypeConverter
import javax.el._

class BaseContext extends ELContext {
  val resolver       = BaseResolver()
  val functionMapper = BaseFunctionMapper()
  val variableMapper = BaseVariableMapper()

  def getELResolver: ELResolver = resolver

  def getFunctionMapper: FunctionMapper = functionMapper

  def getVariableMapper: VariableMapper = variableMapper

  def register(name: String, obj: Any) {
    variableMapper.setVariable(name, createValueExpression(obj))
  }

  def createValueExpression(obj: Any): ObjectValueExpression =
    new ObjectValueExpression(TypeConverter.DEFAULT, obj, obj.getClass)

  def unregister(name: String) {
    variableMapper.removeVariable(name)
  }

}

object BaseContext {
  def apply(): BaseContext = new BaseContext
}
