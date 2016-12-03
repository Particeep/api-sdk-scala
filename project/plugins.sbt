resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"

resolvers += "Typesafe repository plugin" at "https://repo.scala-sbt.org/scalasbt/sbt-plugin-releases/"

resolvers += "scalaz-bintray" at "https://de.bintray.com/scalaz/releases/"

resolvers += Classpaths.sbtPluginReleases

// code plugins

addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "0.7.0" excludeAll(
  ExclusionRule(organization = "com.danieltrinh")))

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.3.5")

// scala lint tool : https://github.com/puffnfresh/wartremover
addSbtPlugin("org.brianmckenna" % "sbt-wartremover" % "0.13")

addSbtPlugin("com.typesafe.sbt" % "sbt-scalariform" % "1.3.0")

libraryDependencies += "org.scalariform" %% "scalariform" % "0.1.7"
