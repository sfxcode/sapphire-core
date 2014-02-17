package com.sfxcode.sapphire.core.el

import javax.el._
import java.lang.reflect.Method
import scala.collection.mutable
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


class BaseFunctionMapper extends  FunctionMapper {
  val map = new mutable.HashMap[String, Method]()

  def resolveFunction(prefix: String, localName: String): Method = {
    map(key(prefix, localName))
  }

  def addFunction(prefix: String, localName: String,method:Method):Option[Method] = {
    map.put(key(prefix, localName), method)
  }

  def key(prefix: String, localName: String):String = {
    "%s:%s".format(prefix, localName)
  }
}

object BaseFunctionMapper {
  def apply():BaseFunctionMapper = new BaseFunctionMapper
}

class BaseVariableMapper extends  VariableMapper {
  val map = new mutable.HashMap[String, ValueExpression]()

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


