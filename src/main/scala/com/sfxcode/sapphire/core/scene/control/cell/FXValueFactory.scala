package com.sfxcode.sapphire.core.scene.control.cell

import javafx.util.Callback
import javafx.scene.control.TableColumn
import javafx.beans.value.ObservableValue
import javafx.scene.control.TableColumn.CellDataFeatures
import scala.beans.BeanProperty
import scalafx.delegate.SFXDelegate
import com.sfxcode.sapphire.core.value.{FXBean, ReflectionTools}
import java.text.DecimalFormat
import javafx.beans.property._
import scalafx.beans.property.LongProperty

class FXValueFactory[S <: AnyRef, T] extends Callback[TableColumn.CellDataFeatures[S, T], ObservableValue[T]] {

  @BeanProperty
  var property = ""

  @BeanProperty
  var format = ""

  lazy val numberFormatter = new DecimalFormat(format)

  def call(features: CellDataFeatures[S, T]) = {
    val value: S = features.getValue

    value match {
      case bean: FXBean[_<:AnyRef] =>
        var p =  bean.getProperty(property)
        if (format.length>0)  {
          p match {
            case intProperty:IntegerProperty => p = new SimpleStringProperty(numberFormatter.format(intProperty.get))
            case longProperty:LongProperty => p = new SimpleStringProperty(numberFormatter.format(longProperty.get))
            case floatProperty:FloatProperty => p = new SimpleStringProperty(numberFormatter.format(floatProperty.get))
            case doubleProperty:DoubleProperty => p = new SimpleStringProperty(numberFormatter.format(doubleProperty.get))
            case _ =>
          }
        }
        p.asInstanceOf[ObservableValue[T]]
      case _ =>
        val reflectedValue = ReflectionTools.getMemberValue(value, property)
        reflectedValue match {
          case ov: ObservableValue[T] => ov
          case d: SFXDelegate[_] => d.delegate.asInstanceOf[ObservableValue[T]]
          case _ => null
        }
    }
  }

}
