package com.sfxcode.sapphire.core.value

import org.specs2.mutable.Specification
import com.sfxcode.sapphire.core.cdi.CDILauncher
import com.sfxcode.sapphire.core.test.PersonDatabase

class PersonenFXBeanSpec extends Specification {
   CDILauncher.init()

   "Personen List of FXBean" should {

     "should filter by FXBeanFunction" in {
       val testPersonen = PersonDatabase.testPersonen

       testPersonen.size must be equalTo 200
       val filtered = testPersonen.filter(p => p.getValue("age") == 25)
       filtered.size must be equalTo 13

     }
   }

 }
