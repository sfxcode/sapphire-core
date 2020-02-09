# Expression Language

Expression Language is based on [Jakarta Expression Language](https://github.com/eclipse-ee4j/el-ri) .

## Functions

EL supports defining own functions in expressions. There is a FunctionHelper in
the Expressions object. Some Functions are predefined.

### Predefined Functions

Functions can have a prefix. The Sapphire Core Functions has the prefix: sf.

@@snip [ExpressionsSpec.scala](../../../../../src/test/scala/com/sfxcode/sapphire/core/el/ExpressionsSpec.scala) { #coreFunction }


### Custom Functions

@@snip [ExpressionsSpec.scala](../../../../../src/test/scala/com/sfxcode/sapphire/core/el/ExpressionsSpec.scala) { #customFunction }

## Base Usage

Expression Language Processing is defined in the Expressions object.

There is also an expressions trait:

@@snip [Expressions.scala](../../../../../src/main/scala/com/sfxcode/sapphire/core/el/Expressions.scala) { #Expressions }

## EL in FXBean



## EL in WindowController / ViewController


