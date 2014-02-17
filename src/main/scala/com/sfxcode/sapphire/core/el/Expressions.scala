package com.sfxcode.sapphire.core.el

import javax.el.{MethodNotFoundException, ExpressionFactory}
import de.odysseus.el.ObjectValueExpression

object Expressions {
  val props = System.getProperties

  if (!props.containsKey("javax.cdi.methodInvocations"))
    props.put("javax.cdi.methodInvocations", "true")

  if (!props.containsKey("javax.cdi.cacheSize"))
    props.put("javax.cdi.cacheSize", "5000")

  val factory = ExpressionFactory.newInstance(props)
  val context = BaseContext()

  val TempObjectName = "_self"
  val TempValueName = "_tempValue"
  val ExpressionPrefix = "${"

  def register(name: String, obj: Any) {
    context.register(name, obj)
  }

  def unregister(name: String) {
    context.unregister(name)
  }

  def createValueExpression(obj: AnyRef): ObjectValueExpression = {
    context.createValueExpression(obj)
  }

  def getValue(expression: String, clazz: Class[AnyRef] = classOf[Object]): Any = {
    val ve = factory.createValueExpression(context, expression, clazz)
    ve.getValue(context)
  }

  private def getValueOnObject(obj: AnyRef, expression: String, clazz: Class[AnyRef] = classOf[Object]): Any = {
    val tempObjectString = "%s_%s".format(TempObjectName, Math.abs(obj.hashCode()))
    val newExpression = expression.replace(TempObjectName, tempObjectString)
    register(tempObjectString, obj)
    val ve = factory.createValueExpression(context, newExpression, clazz)
    val result = ve.getValue(context)
    unregister(tempObjectString)
    result
  }

  def evaluateExpressionOnObject(obj: AnyRef, exp: String, clazz: Class[AnyRef] = classOf[Object]): Any = {

    var expression = exp
    while (expression.contains("Exp{"))
      expression = expression.replace("Exp{", "${")
    var result: Any = null
    if (expression.contains(ExpressionPrefix)) {
      result = getValueOnObject(obj, expression, clazz)
    }
    else if (expression.contains("("))
      result = getValueOnObject(obj, String.format("${%s.%s}", TempObjectName, expression), clazz)
    else {
      var tempExpression = expression
      while (tempExpression.indexOf(".") != -1 && tempExpression.indexOf("().") == -1) {
        val index = tempExpression.indexOf(".")
        tempExpression = tempExpression.substring(0, index) + "()" + tempExpression.substring(index)
      }


      try {
        var methodExpression = tempExpression
        if (!methodExpression.endsWith("()"))
          methodExpression = methodExpression + "()"
        result = getValueOnObject(obj, String.format("${%s.%s}", TempObjectName, methodExpression), clazz)
      }
      catch {
        case e: MethodNotFoundException =>
          if (!tempExpression.endsWith("()")) {
            result = getValueOnObject(obj, String.format("${%s.%s}", TempObjectName, tempExpression), clazz)
          }
      }
    }
    result
  }


}

