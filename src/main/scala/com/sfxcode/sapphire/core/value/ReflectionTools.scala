package com.sfxcode.sapphire.core.value

import com.typesafe.scalalogging.LazyLogging

import scala.reflect.runtime.universe._
import scala.reflect.runtime.{universe => ru}

object ReflectionTools extends LazyLogging {
  val typeMirror = ru.runtimeMirror(this.getClass.getClassLoader)

  def getFieldType(target: Any, name: String,showMethods:Boolean=false): Option[Type] = {

    val classMember = typeMirror.classSymbol(target.getClass).toType.members.find(m => !m.isMethod && m.name.toString.trim.equals(name))
    if (classMember.isDefined)
      return Some(classMember.get.typeSignature)

    if (showMethods) {
      val traitMember = typeMirror.classSymbol(target.getClass).toType.members.find(m => m.name.toString.trim.equals(name))
      if (traitMember.isDefined)
        return Some(traitMember.get.typeSignature)
    }
    None
  }

  def getFieldMirror(target: Any, name: String): FieldMirror = {

    val t = typeMirror.classSymbol(target.getClass).toType
    val symbol = t.decl(ru.TermName(name)).asTerm
    val instanceMirror = typeMirror.reflect(target)
    instanceMirror.reflectField(symbol)
  }

  def getMemberValue(target: Any, name: String): Any = {
    getFieldMirror(target, name).get
  }

  def setMemberValue(target: Any, name: String, value:Any) {
    val memberInfo = FXBeanClassRegistry.memberInfo(target, name)
    val fieldMirror = getFieldMirror(target, name)
    if (memberInfo.isOption) {
      if (value == null)
        fieldMirror.set(None)
      else
        fieldMirror.set(Some(value))
    }
    else {
      try {
        fieldMirror.set(value)
      }
      catch {
        case e:Exception => logger.trace("can not update %s for field %s".format(value, name))
      }
    }

  }

  def getMembers[T <: AnyRef]()(implicit t: TypeTag[T]):List[Symbol] = {
    val bindings = new KeyBindings
    typeOf[T].members.collect {
      case m: MethodSymbol if m.isCaseAccessor => m
    }.toList }



}
