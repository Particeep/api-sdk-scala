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

### Others

You'll need to manually install the following JARs:

* Download the JAR from https://github.com/particeep/api-scala-client/releases/latest
* [Google Gson](http://code.google.com/p/google-gson/) from <http://google-gson.googlecode.com/files/google-gson-2.2.4-release.zip>.


## Documentation

Please see the [Particeep API docs](https://www.particeep.com/en/docs) for the most up-to-date documentation.

## Usage

ParticeepExample.scala

```scala
import java.util.HashMap;
import java.util.Map;

public class ParticeepExample {

    public static void main(String[] args) {
        // todo
        try {
            // todo
            System.out.println(charge);
        } catch (ParticeepException e) {
            e.printStackTrace();
        }
    }
}
```

See [ParticeepTest.scala](https://github.com/particeep/api-scala-client/blob/master/src/test/scala/com/particeep/ParticeepTest.java) for more examples.

## Testing

You must have Sbt installed. To run the tests:

    sbt test

You can run particular tests by using `testOnly pkg.ClassName -- -z update`. Make sure you use the fully qualified class name to differentiate between
unit and functional tests. For example:

    sbt "testOnly kpi.KpiTest -- -z update"
