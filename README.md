# Particeep API Client for Scala

You can sign up for a Particeep account at https://admin.particeep.com

## Requirements

Java 1.8 or later.


### Play dependencies

By default this lib depends on Play framework.

If you don't use Play!, you can checkout the branch `https://github.com/Particeep/api-sdk-scala/tree/feature/stand_alone`
You'll be able to use the lib without bringing all the Play framework into your code base. The dependancies in the stand_alone branch
is limited to 2 libraries extracted from Play project.

* https://github.com/playframework/play-ws
* https://github.com/playframework/play-json

If you want to use your own json parser and http wrapper, you'll better build everything by yourself

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

    def basicUsage() {
        val ws = ParticeepApi.test(apiKey, apiSecret)
        val result:Future[Either[JsError, User]] = ws.user.byId("some_user_id")

        // to switch to prod env just do
        val ws_prod = ParticeepApi.prod(apiKey, apiSecret)
    }

    // if you need only a subset of the endpoints of the api and don't want to be
    // bothered with the other you can create a custom client
    def customUsage() {
        val creds = ApiCredential(apiKey, apiSecret)

        // here you have access only to User and Info endpoints
        val ws = new ApiClient(
            baseUrl = "https://api.particeep.com", // prod url
            apiCredential = creds,
            version = "1"
        ) with InfoCapability with UserCapability

        val result:Future[Either[JsError, User]] = ws.user.byId("some_user_id")
    }
}
```

See the [tests](https://github.com/particeep/api-scala-client/blob/master/src/test/scala/com/particeep/test) for more examples.

If you need to customize the http client, take a look at [ApiClient.scala](https://github.com/particeep/api-scala-client/blob/master/src/main/scala/com/particeep/core/ApiClient.scala)


## Testing

You must have Sbt installed. To run the tests:

    sbt test

You can run particular tests by using `testOnly pkg.ClassName -- -z update`. Make sure you use the fully qualified class name to differentiate between
unit and functional tests. For example:

    sbt "testOnly com.particeep.test.ParticeepTest -- -z update"
