# Particeep API Client for Scala

You can sign up for a Particeep account at https://setup.particeep.com

## Requirements

Java 1.8 or later.

## Installation

### Sbt users

Add this dependency to your project's POM:

```scala
libraryDependencies += "com.particeep" %% "api-scala-client" % "1.0.0"
```

Or add the project as a dependency

```scala
lazy val root = Project("root", file("."))
                    .dependsOn(apiParticeepClient)

lazy val apiParticeepClient = RootProject(uri("https://github.com/Particeep/api-sdk-scala.git"))

```

### Others

You'll need to manually install the following JARs and dependencies :

* Download the JAR from https://github.com/particeep/api-scala-client/releases/latest
* "com.typesafe.play" %% "play-ws"    % "2.4.8"
* "com.github.driox"  %% "sorus"      % "1.0.0"
* "org.slf4j"         %  "slf4j-api"  % "1.7.21"

NB : You also need transitive dependencies of play-ws

## Documentation

Please see the [Particeep API docs](https://www.particeep.com/en/docs) for the most up-to-date documentation.

## Usage

ParticeepExample.scala

```scala
import com.particeep.api.core._
import com.particeep.api._

object ParticeepExample {

    def test() {
        val creds = ApiCredential(apiKey, apiSecret)

        val ws = new ApiClient(
            "https://api.particeep.com",
            creds,
            "1"
        ) with InfoClient

        val result:Future[Either[JsError, Info]] = ws.info()
    }
}
```

See [ParticeepTest.scala](https://github.com/particeep/api-scala-client/blob/master/src/test/scala/com/particeep/ParticeepTest.java) for more examples.

## Testing

You must have Sbt installed. To run the tests:

    sbt test

You can run particular tests by using `testOnly pkg.ClassName -- -z update`. Make sure you use the fully qualified class name to differentiate between
unit and functional tests. For example:

    sbt "testOnly com.particeep.test.ParticeepTest -- -z update"
