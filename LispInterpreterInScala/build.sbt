
name := "LispInterpreterInScala"

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "org.scala-lang.modules" % "scala-parser-combinators_2.11" % "1.0.4",
  ("org.scala-lang" % "jline" % "2.10.6")
    .exclude("org.fusesource.jansi", "jansi")
    .exclude("jline", "jline"),
  "org.clapper" %% "argot" % "1.0.4"
)
