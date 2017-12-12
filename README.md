# Particeep API Client for Scala

You can sign up for a Particeep account at https://admin.particeep.com

## Requirements

Java 1.8 or later.

## Installation

### Sbt users

Add the project as a dependency

```scala
lazy val root = Project("root", file("."))
                    .dependsOn(apiParticeepClient)

lazy val apiParticeepClient = RootProject(uri("https://github.com/Particeep/api-sdk-scala.git#feature/stand_alone"))

```

### Others

You'll need to manually install the following JARs and dependencies :

* Download the JAR from https://github.com/particeep/api-scala-client/releases/latest
* "com.typesafe.play" %% "play-ahc-ws-standalone"          % "1.1.3"
* "com.typesafe.play" %% "play-ws-standalone-json"         % "1.1.3"
* "com.github.driox"  %% "sorus"      % "1.0.0"
* "org.slf4j"         %  "slf4j-api"  % "1.7.21"
* "ai.x"              %% "play-json-extensions"            % "0.10.0"

NB : You also need transitive dependencies of these dependencies

## Documentation

Please see the [Particeep API docs](https://www.particeep.com/en/docs) for the most up-to-date documentation.

## Usage

ParticeepExample.scala

```scala
import com.particeep.api.core._
import com.particeep.api._

object ParticeepExample {

    def basicUsage()(implicit system:ActorSystem) {
        implicit val materializer: akka.stream.Materializer = ActorMaterializer()
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
        val ws = ApiClient(
            baseUrl = "https://api.particeep.com", // prod url
            apiCredential = creds,
            version = "1"
        ) with InfoCapability with UserCapability

        val result:Future[Either[JsError, User]] = ws.user.byId("some_user_id")
    }
}
```

/!\ Warning

When you do
```
val ws = ApiClient(
            baseUrl = "https://api.particeep.com", // prod url
            apiCredential = creds,
            version = "1"
        )
```
You use the more simple way and the lib will create its own actor system named "particeep-api-client".
You don't want to build actor system multiple time so don't create multiple instance of ApiClient this way

For huge application we recommend you to build your own actor system with a tailored config and pass it as a parameter

```
val ws = ApiClient(
            system,
            baseUrl = "https://api.particeep.com", // prod url
            apiCredential = creds,
            version = "1"
        )
```


See the [tests](https://github.com/particeep/api-scala-client/blob/master/src/test/scala/com/particeep/test) for more examples.

If you need to customize the http client, use it like that

```
// the lib https://github.com/playframework/play-ws will explain how to build and customise a StandaloneAhcWSClient
val http_client:StandaloneAhcWSClient = ???

val ws = ApiClient(
            http_client
            baseUrl = "https://api.particeep.com", // prod url
            apiCredential = creds,
            version = "1"
        ) with InfoCapability with UserCapability

```



## Testing

You must have Sbt installed. To run the tests:

    sbt test

You can run particular tests by using `testOnly pkg.ClassName -- -z update`. Make sure you use the fully qualified class name to differentiate between
unit and functional tests. For example:

    sbt "testOnly com.particeep.test.ParticeepTest -- -z update"
