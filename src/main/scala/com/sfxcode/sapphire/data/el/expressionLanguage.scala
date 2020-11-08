package com.sfxcode.sapphire.data.el

import java.util.Properties

import javax.el._

import scala.reflect.ClassTag

object Expressions {
  val props: Properties = System.getProperties

  if (!props.containsKey("org.apache.el.BeanELResolver.CACHE_SIZE"))
    props.put("org.apache.el.BeanELResolver.CACHE_SIZE", "2000")

  if (!props.containsKey("org.apache.el.ExpressionBuilder.CACHE_SIZE"))
    props.put("org.apache.el.ExpressionBuilder.CACHE_SIZE", "10000")

  val processor: ELProcessor = new ELProcessor
  val manager: ELManager = processor.getELManager
  val factory: ExpressionFactory = ExpressionFactory.newInstance(props)
  val context: StandardELContext = manager.getELContext

  val functionHelper: FunctionHelper = FunctionHelper(processor)

  def getValue(expression: String, clazz: Class[AnyRef] = classOf[Object]): Option[Any] = {
    val ve = factory.createValueExpression(context, expression, clazz)
    Option(ve.getValue(context))
  }

  def setValue(expression: String, value: Any, clazz: Class[AnyRef] = classOf[Object]): Unit = {
    val ve = factory.createValueExpression(context, expression, clazz)
    ve.setValue(context, value)
  }

  def evaluateExpressionOnObject[T <: Any](
    obj: AnyRef,
    expression: String,
    clazz: Class[AnyRef] = classOf[Object]): Option[T] = {

    val result: Option[Any] = ObjectExpressionHelper.getValue(obj, expression: String, clazz)

    if (result.isDefined && result.get.isInstanceOf[T])
      result.asInstanceOf[Option[T]]
    else
      None
  }

  def register(name: String, obj: Any): Unit =
    processor.setValue(name, obj)

  def unregister(name: String): Unit =
    processor.setValue(name, null)

  def registerBean(bean: AnyRef): Unit = processor.defineBean(beanName(bean), bean)

  def unregisterBean(bean: AnyRef): Unit =
    processor.defineBean(beanName(bean), null)

  def registeredBean[T <: AnyRef]()(implicit ct: ClassTag[T]): Option[T] = {

    val simpleName = ct.runtimeClass.getSimpleName
    val key = "%s%s".format(simpleName.head.toLower, simpleName.tail)
    val bean = evaluateExpressionOnObject(this, "${%s}".format(key))

    if (bean.isDefined && bean.get.isInstanceOf[T])
      Some(bean.get.asInstanceOf[T])
    else
      None

  }

  private def beanName(bean: AnyRef): String = {
    val simpleName = bean.getClass.getSimpleName
    "%s%s".format(simpleName.head.toLower, simpleName.tail)
  }

}

// #Expressions
trait Expressions {

  def getExpressionValue(expression: String, clazz: Class[AnyRef] = classOf[Object]): Option[Any] =
    Expressions.getValue(expression, clazz)

  def setExpressionValue(expression: String, value: Any, clazz: Class[AnyRef] = classOf[Object]): Unit =
    Expressions.setValue(expression, value, clazz)

  def register(name: String, obj: Any): Unit = Expressions.register(name: String, obj: Any)

  def unregister(name: String): Unit = Expressions.unregister(name)

  def registerBean(bean: AnyRef): Unit = Expressions.registerBean(bean)

  def unregisterBean(bean: AnyRef): Unit = Expressions.unregisterBean(bean)

  def registeredBean[T <: AnyRef]()(implicit ct: ClassTag[T]): Option[T] =
    Expressions.registeredBean[T]()

  def evaluateExpressionOnObject[T <: Any](source: AnyRef, expression: String): Option[T] =
    Expressions.evaluateExpressionOnObject[T](source, expression)
}

// #Expressions
