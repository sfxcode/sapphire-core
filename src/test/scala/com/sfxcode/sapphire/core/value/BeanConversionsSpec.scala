package com.sfxcode.sapphire.core.value

import javafx.collections.ObservableList
import org.specs2.mutable.Specification

class BeanConversionsSpec extends Specification with BeanConversions {

  "BeanConversions" should {

    "convert Bean To FXBean and back " in {
      val bean: FXBean[TestBean] = TestBean()
      bean.getValue("name") must be equalTo "test"
      val convertedBean: TestBean = bean
      convertedBean.name must be equalTo "test"
    }

    "convert Bean List to ObservableList " in {
      val testBean = TestBean()
      val list     = List[TestBean](testBean)

      val observableList: ObservableList[FXBean[TestBean]] = list

      observableList.head must be equalTo testBean

    }

  }

}
