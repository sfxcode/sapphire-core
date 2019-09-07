# FXBean

FXBean is one of the core concepts of this framework.
It is an adapter for Java/Scala Beans (Maps) for automatic JavaFX Binding. FXBean does all the JavaFX Property Binding for your Application.

## Features

- Every Java / Scala Bean  can be used for FXBean
- FXBean has additional support for Java / Scala Maps
- FXBean resolves Expressions on bean
- FXBean creates Properties needed for Binding on demand
- FXBean has change management by default

@@@ note { title=Hint }

Use Sapphire Includes for implicit Bean to FXBean Conversion

@@@

## Example

```scala
case class Author(name: String)
case class Book(id: Long, title: String, pages: Int, author: Author)

// create FXBean for sample case class
val scalaBook = Book(1, "Programming In Scala", 852, Author("Martin Odersky"))
val book = FXBean[Book](scalaBook)

// getValue and updateValue are used for bean property access and modification
// getProperty, getStringProperty ... 
// are used for automatic create a JavaFX Property
val title = book.getValue("title")
// "Programming In Scala"

val titleProperty = book.getStringProperty("title")

book.updateValue("title", "Programming In Scala 3.0")
// title is updated, titleProperty as well

val newTitle = book.getValue("title")
// "Programming In Scala 3.0"

val newTitleFromProperty = titleProperty.getValue  // "Programming In Scala 3.0"

// getValue and updateValue for underlying class by dot syntax
val authorName = book.getValue("author.name")  // "Martin Odersky"


```



