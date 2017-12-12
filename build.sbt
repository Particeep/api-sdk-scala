name := """api-sdk-scala"""

version := "1.0.0"

scalaVersion := "2.11.8"

resolvers += "Typesafe Releases" at "https://repo.typesafe.com/typesafe/releases/"

// Change this to another test framework if you prefer
libraryDependencies ++= Seq(
  "org.scalatest"     %% "scalatest"                       % "2.2.4"  % "test" withSources(),
  "com.typesafe.akka" %% "akka-testkit"                    % "2.4.20" % "test" withSources(),
  "com.typesafe.play" %% "play-ahc-ws-standalone"          % "1.1.3"           withSources(),
  "com.typesafe.play" %% "play-ws-standalone-json"         % "1.1.3"           withSources(),
  "com.ning"          %  "async-http-client"               % "1.9.40"          withSources(),
  "com.github.driox"  %% "sorus"                           % "1.0.0"           withSources(),
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
