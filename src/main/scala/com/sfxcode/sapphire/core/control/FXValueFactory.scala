package com.sfxcode.sapphire.core.control

import java.text.DecimalFormat

import com.sfxcode.sapphire.core.value.{FXBean, ReflectionTools}
import javafx.beans.property.{LongProperty, _}
import javafx.beans.value.ObservableValue
import javafx.scene.control.TableColumn
import javafx.scene.control.TableColumn.CellDataFeatures
import javafx.util.Callback

import scala.beans.BeanProperty

class FXValueFactory[S <: AnyRef, T] extends Callback[TableColumn.CellDataFeatures[S, T], ObservableValue[T]] {

  @BeanProperty
  var property = ""

  @BeanProperty
  var format = ""

  lazy val numberFormatter = new DecimalFormat(format)

  def call(features: CellDataFeatures[S, T]) = {
    val value: S = features.getValue

    value match {
      case bean: FXBean[_] =>
        var p = bean.getProperty(property)
        if (format.length > 0) {
          p match {
            case intProperty: IntegerProperty => p = new SimpleStringProperty(numberFormatter.format(intProperty.get))
            case longProperty: LongProperty => p = new SimpleStringProperty(numberFormatter.format(longProperty.get))
            case floatProperty: FloatProperty => p = new SimpleStringProperty(numberFormatter.format(floatProperty.get))
            case doubleProperty: DoubleProperty => p = new SimpleStringProperty(numberFormatter.format(doubleProperty.get))
            case _ =>
          }
        }
        p.asInstanceOf[ObservableValue[T]]
      case _ =>
        val reflectedValue = ReflectionTools.getMemberValue(value, property)
        reflectedValue match {
          case ov: ObservableValue[T] => ov
          case _ => null
        }
    }
  }

}
