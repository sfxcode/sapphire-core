package com.sfxcode.sapphire.data.reflect

import java.lang.reflect.Field

import com.sfxcode.sapphire.data.reflect.PropertyType.{ PropertyValue, TypeUnknown }

case class FieldMeta(
  name: String,
  signature: PropertyValue = TypeUnknown,
  isOption: Boolean = false,
  field: Field = null)

object FieldMeta {
  val EmptyTypeHints = List[FieldMeta]()

}
