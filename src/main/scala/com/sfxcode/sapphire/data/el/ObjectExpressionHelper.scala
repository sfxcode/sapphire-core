package com.sfxcode.sapphire.data.el

import javax.el.MethodNotFoundException

object ObjectExpressionHelper {
  val TempObjectName = "_self"
  val TempValueName = "_tempValue"
  val ExpressionPrefix = "${"
  val FxmlExpressionPrefix: String = "!{"

  def getValue(obj: AnyRef, expressionString: String, clazz: Class[AnyRef]): Option[Any] = {
    var result: Option[Any] = None

    val expression = expressionString.replace(FxmlExpressionPrefix, ExpressionPrefix)

    if (expression.contains(ExpressionPrefix))
      result = getValueOnObject(obj, expression, clazz)
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
      } catch {
        case _: MethodNotFoundException =>
          if (!tempExpression.endsWith("()"))
            result = getValueOnObject(obj, String.format("${%s.%s}", TempObjectName, tempExpression), clazz)
      }
    }
    result
  }

  private def getValueOnObject(obj: AnyRef, expression: String, clazz: Class[AnyRef]): Option[Any] = {
    val tempObjectString = "%s_%s".format(TempObjectName, Math.abs(obj.hashCode()))
    val newExpression = expression.replace(TempObjectName, tempObjectString)
    Expressions.register(tempObjectString, obj)
    val result = Expressions.getValue(newExpression, clazz)
    Expressions.unregister(tempObjectString)
    result
  }

  def isExpressionKey(key: String): Boolean =
    key.contains(ObjectExpressionHelper.ExpressionPrefix) || key.contains(ObjectExpressionHelper.FxmlExpressionPrefix)
}
