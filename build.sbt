name := """api-sdk-scala"""

version := "2.0.6"

scalaVersion := "2.11.8"

resolvers += "Typesafe Releases" at "https://repo.typesafe.com/typesafe/releases/"

// Change this to another test framework if you prefer
libraryDependencies ++= Seq(
  "org.scalatest"     %% "scalatest"                       % "2.2.4"  % "test" withSources(),
  "com.typesafe.play" %% "play-ahc-ws-standalone"          % "2.0.0-M2"        withSources() exclude("com.ning", "async-http-client"),
  "com.typesafe.play" %% "play-ws-standalone-json"         % "2.0.0-M2"        withSources(),
  "org.slf4j"         %  "slf4j-api"                       % "1.7.21"          withSources(),
  "ai.x"              %% "play-json-extensions"            % "0.10.0"          withSources()
)

// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"

// sbt and compiler option
scalacOptions ++= Seq(
    "-deprecation",
    "-feature",
    "-unchecked",
    "-Xfatal-warnings",
    "-Xlint",
    "-Ywarn-dead-code",
    "-Ywarn-unused",
    "-Ywarn-unused-import"
)

// ~~~~~~~~~~~~~~~~~
//Scalariform config

import com.typesafe.sbt.SbtScalariform
import com.typesafe.sbt.SbtScalariform.ScalariformKeys

scalariformSettings

ScalariformKeys.preferences := ScalariformKeys.preferences.value
  .setPreference(scalariform.formatter.preferences.AlignSingleLineCaseStatements, true)
  .setPreference(scalariform.formatter.preferences.AlignParameters, true)
  .setPreference(scalariform.formatter.preferences.DoubleIndentClassDeclaration, true)
  .setPreference(scalariform.formatter.preferences.PreserveDanglingCloseParenthesis, true)
