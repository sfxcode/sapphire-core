package com.sfxcode.sapphire.core.el

import javax.el._

import scala.reflect.ClassTag

object Expressions {
  val props = System.getProperties

  if (!props.containsKey("javax.cdi.methodInvocations")) {
    props.put("javax.cdi.methodInvocations", "true")
  }

  if (!props.containsKey("javax.cdi.cacheSize")) {
    props.put("javax.cdi.cacheSize", "5000")
  }

  val processor: ELProcessor = new ELProcessor
  val manager: ELManager = processor.getELManager
  val factory: ExpressionFactory = ExpressionFactory.newInstance(props)
  val context: StandardELContext = manager.getELContext

  val functionHelper: FunctionHelper = FunctionHelper(processor)

  val TempObjectName = "_self"
  val TempValueName = "_tempValue"
  val ExpressionPrefix = "${"
  val FxmlExpressionPrefix: String = "!{"

  def getValue(expression: String, clazz: Class[AnyRef] = classOf[Object]): Any = {
    val ve = factory.createValueExpression(context, expression, clazz)
    ve.getValue(context)
  }

  def evaluateExpressionOnObject(obj: AnyRef, exp: String, clazz: Class[AnyRef] = classOf[Object]): Any = {

    var expression = exp
    while (expression.contains(FxmlExpressionPrefix)) expression = exp.replace(FxmlExpressionPrefix, ExpressionPrefix)
    var result: Any = null
    if (expression.contains(ExpressionPrefix)) {
      result = getValueOnObject(obj, expression, clazz)
    } else if (expression.contains("("))
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
        case e: MethodNotFoundException =>
          if (!tempExpression.endsWith("()")) {
            result = getValueOnObject(obj, String.format("${%s.%s}", TempObjectName, tempExpression), clazz)
          }
      }
    }
    result
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

  def register(name: String, obj: Any): Unit =
    processor.setValue(name, obj)

  def unregister(name: String): Unit =
    processor.setValue(name, null)

  def registerBean(bean: AnyRef): Unit =
    processor.defineBean(beanName(bean), bean)

  def unregisterBean(bean: AnyRef): Unit =
    processor.defineBean(beanName(bean), null)

  def registeredBean[T <: AnyRef]()(implicit ct: ClassTag[T]): Option[T] = {

    val simpleName = ct.runtimeClass.getSimpleName
    val key = "%s%s".format(simpleName.head.toLower, simpleName.tail)
    val bean = evaluateExpressionOnObject(this, "${%s}".format(key))

    if (bean != null && bean.isInstanceOf[T]) {
      Option[T](bean.asInstanceOf[T])
    } else {
      None
    }

  }

  private def beanName(bean: AnyRef): String = {
    val simpleName = bean.getClass.getSimpleName
    "%s%s".format(simpleName.head.toLower, simpleName.tail)
  }

}

// #Expressions
trait Expressions {

  def register(name: String, obj: Any): Unit = Expressions.register(name: String, obj: Any)

  def unregister(name: String): Unit = Expressions.unregister(name)

  def registerBean(bean: AnyRef): Unit = Expressions.registerBean(bean)

  def unregisterBean(bean: AnyRef): Unit = Expressions.unregisterBean(bean)

  def registeredBean[T <: AnyRef]()(implicit ct: ClassTag[T]): Option[T] =
    Expressions.registeredBean[T]()

  def evaluateExpression(source: AnyRef, expression: String): Any =
    Expressions.evaluateExpressionOnObject(source, expression)
}

// #Expressions
