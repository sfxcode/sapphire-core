name := "project-name"

organization := "your.organization"

version := "1.0.0"

scalaVersion := "2.12.8"

resolvers += "sfxcode-bintray" at "https://dl.bintray.com/sfxcode/maven"

val JavaFXVersion = "12.0.1"

val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux")   => "linux"
  case n if n.startsWith("Mac")     => "mac"
  case n if n.startsWith("Windows") => "win"
  case _                            => throw new Exception("Unknown platform!")
}

libraryDependencies ++= Seq("base", "controls", "fxml", "graphics", "media", "swing", "web").map(m =>
  "org.openjfx" % s"javafx-$m" % JavaFXVersion % Provided classifier osName
)

// Test

libraryDependencies += "org.specs2" %% "specs2-core" % "4.5.1" % Test

// Compile

libraryDependencies += "com.sfxcode.sapphire" %% "sapphire-core" % "1.5.0"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"
