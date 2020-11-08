package com.sfxcode.sapphire.data.reflect

import com.typesafe.scalalogging.LazyLogging

object ReflectionTools extends LazyLogging {

  def getFieldValue(target: Any, name: String): Any = {
    val fieldMeta = FieldMetaRegistry.fieldMeta(target.asInstanceOf[AnyRef], name)
    val field = fieldMeta.field
    field.setAccessible(true)
    field.get(target)
  }

  def setFieldValue(target: Any, name: String, value: Any) {
    val fieldMeta = FieldMetaRegistry.fieldMeta(target.asInstanceOf[AnyRef], name)
    val field = fieldMeta.field

    try {
      field.setAccessible(true)

      if (fieldMeta.isOption)
        if (value == null)
          field.set(target, None)
        else if (value.isInstanceOf[Option[_]])
          field.set(target, value)
        else
          field.set(target, Some(value))
      else
        field.set(target, value)
    } catch {
      case _: Exception => logger.debug("can not update %s for field %s".format(value, name))
    }

  }

}
