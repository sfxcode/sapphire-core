# Expression Language

Expression Language is based on [Jakarta Expression Language](https://github.com/eclipse-ee4j/el-ri) .

## Functions

EL supports defining own functions in expressions. There is a FunctionHelper in
the Expressions object. Some Functions are predefined.

### Predefined Functions

Functions can have a prefix. The Sapphire Core Functions has the prefix: sf.

@@snip [ExpressionsSpec.scala](../../../../../src/test/scala/com/sfxcode/sapphire/core/el/ExpressionsSpec.scala) { #coreFunction }


| function         | sample                                 | info                        |
|:-----------------|:---------------------------------------|:----------------------------|
| frameworkName    | ${sf:frameworkName()}                  |                             |
| frameworkVersion | ${sf:frameworkVersion()}               |                             |
| dateString       | ${sf:dateString(testDate)}             | Default Pattern: YYYY-MM-dd |
| now              | ${sf:frameworkName()}                  |                             |
| nowAsString      | ${sf:nowAsString()}").toString         | Default Pattern: YYYY-MM-dd |
| boolString       | ${sf:boolString(testBoolean,'Y', 'N')} |                             |
| configString     | ${sf:configString('test.string')}      |                             |
| i18n             | ${sf:i18n('personText')}               |                             |


### Custom Functions

@@snip [ExpressionsSpec.scala](../../../../../src/test/scala/com/sfxcode/sapphire/core/el/ExpressionsSpec.scala) { #customFunction }

## Base Usage

Expression Language Processing is defined in the Expressions object.

There is also an expressions trait:

@@snip [expressionLanguage.scala](../../../../../src/main/scala/com/sfxcode/sapphire/core/el/expressionLanguage.scala) { #Expressions }

## EL in FXBean

@@snip [FXBeanSpec.scala](../../../../../src/test/scala/com/sfxcode/sapphire/core/value/FXBeanSpec.scala) { #FXBeanExpression }


## EL in WindowController / ViewController

WindowController- and ViewController-Beans are automatically registered by name


